package net.xelnaga.httpimposter

import com.google.gson.Gson
import net.xelnaga.httpimposter.factory.RequestPatternFactory
import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.marshaller.RequestPatternMarshaller
import net.xelnaga.httpimposter.marshaller.ResponsePresetMarshaller
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset
import org.apache.log4j.Logger

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HttpImposter {

    Logger log = Logger.getLogger(HttpImposter)

    static final NO_MATCH = new ResponsePreset(
            status: HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
            headers: [
                    new HttpHeader('Content-Type', 'text/plain')
            ],
            body: 'No match found for http request'
    )

    Gson gson = new Gson()

    RequestPatternFactory requestPatternFactory = new RequestPatternFactory()
    RequestPatternMarshaller requestPatternMarshaller = new RequestPatternMarshaller()
    ResponsePresetMarshaller responsePresetMarshaller = new ResponsePresetMarshaller()

    ResponseWriter responseWriter = new ResponseWriter()

    private Map<RequestPattern, ResponsePreset> expectations = [:]
    private int unmatched

    HttpImposter() {
        reset()
    }

    void setFilter(HttpHeaderFilter filter) {

        requestPatternFactory.filter = filter
        requestPatternMarshaller.filter = filter
    }

    void expect(HttpServletRequest httpRequest) {

        Map json = gson.fromJson(httpRequest.inputStream.text, HashMap)

        RequestPattern requestPattern = requestPatternMarshaller.fromJson(json.request)
        ResponsePreset responsePreset = responsePresetMarshaller.fromJson(json.response)

        expectations.put(requestPattern, responsePreset)
    }

    void expect(RequestPattern requestPattern, ResponsePreset responsePreset) {
        expectations[requestPattern] = responsePreset
    }

    void interact(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        RequestPattern requestPattern = requestPatternFactory.fromHttpRequest(httpRequest)
        ResponsePreset responsePreset = expectations.get(requestPattern)

        if (responsePreset) {
            logInteraction(requestPattern, responsePreset, true)
            responseWriter.write(responsePreset, httpResponse)
        } else {
            unmatched++
            logInteraction(requestPattern, NO_MATCH, false)
            responseWriter.write(NO_MATCH, httpResponse)
        }
    }

    boolean verify() {
        return unmatched > 0
    }

    ResponsePreset get(RequestPattern requestPattern) {
        return expectations[requestPattern]
    }

    void reset() {

        expectations.clear()
        unmatched = 0
    }

    private void logInteraction(RequestPattern requestPattern, ResponsePreset responsePreset, boolean matched) {

        log.info matched ? '\n>> [Http Imposter]: Matched Request' : '\n>> [Http Imposter]: Unmatched Request'
        log.info '>> ==================================='
        log.info requestPattern.toString()
        log.info '>>'

        log.info '\n>> [Http Imposter]: Sending Response'
        log.info '>> ==================================='
        log.info responsePreset.toString()
        log.info '>>'
    }
}

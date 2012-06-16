package net.xelnaga.httpimposter

import com.google.gson.Gson
import net.xelnaga.httpimposter.factory.RequestPatternFactory
import net.xelnaga.httpimposter.marshaller.ResponsePresetMarshaller
import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset
import org.apache.log4j.Logger

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import net.xelnaga.httpimposter.marshaller.RequestPatternMarshaller

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

    private Map<RequestPattern, ResponsePreset> map = [:]
    private int unmatched

    HttpImposter() {
        reset()
    }

    void setFilter(HttpHeaderFilter filter) {
        requestPatternFactory = new RequestPatternFactory(filter: filter)
    }

    void respond(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        RequestPattern requestPattern = requestPatternFactory.fromHttpRequest(httpRequest)
        ResponsePreset responsePreset = map.get(requestPattern)

        if (responsePreset) {
            logInteraction(requestPattern, responsePreset, true)
            responseWriter.write(responsePreset, httpResponse)
        } else {
            unmatched++
            logInteraction(requestPattern, NO_MATCH, false)
            responseWriter.write(NO_MATCH, httpResponse)
        }
    }

    void configure(HttpServletRequest httpRequest) {

        Map json = gson.fromJson(httpRequest.inputStream.text, HashMap)
        
        RequestPattern requestPattern = requestPatternMarshaller.fromJson(json.request)
        ResponsePreset responsePreset = responsePresetMarshaller.fromJson(json.response)

        map.put(requestPattern, responsePreset)
    }

    void put(RequestPattern requestPattern, ResponsePreset responsePreset) {
        map[requestPattern] = responsePreset
    }

    ResponsePreset get(RequestPattern requestPattern) {
        return map[requestPattern]
    }

    boolean hasUnmatched() {
        return unmatched > 0
    }

    void reset() {
        map.clear()
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

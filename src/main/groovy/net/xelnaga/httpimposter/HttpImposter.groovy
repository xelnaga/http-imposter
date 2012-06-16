package net.xelnaga.httpimposter

import com.google.gson.Gson
import net.xelnaga.httpimposter.factory.ImposterRequestFactory
import net.xelnaga.httpimposter.factory.ImposterResponseFactory
import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.ImposterRequest
import net.xelnaga.httpimposter.model.ImposterResponse
import org.apache.log4j.Logger

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import groovy.json.JsonSlurper

class HttpImposter {

    Logger log = Logger.getLogger(HttpImposter)

    static final NO_MATCH = new ImposterResponse(
            status: HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
            headers: [
                    new HttpHeader('Content-Type', 'text/plain')
            ],
            body: 'No match found for http request'
    )

    Gson gson = new Gson()
    ImposterRequestFactory requestFactory = new ImposterRequestFactory()
    ImposterResponseFactory responseFactory = new ImposterResponseFactory()

    ResponseWriter responseWriter = new ResponseWriter()

    private Map<ImposterRequest, ImposterResponse> map = [:]
    private int unmatched

    HttpImposter() {
        reset()
    }

    void setFilter(HttpHeaderFilter filter) {
        requestFactory = new ImposterRequestFactory(filter: filter)
    }

    void respond(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        ImposterRequest imposterRequest = requestFactory.fromHttpRequest(httpRequest)
        ImposterResponse imposterResponse = map.get(imposterRequest)

        if (imposterResponse) {
            logInteraction(imposterRequest, imposterResponse, true)
            responseWriter.write(imposterResponse, httpResponse)
        } else {
            unmatched++
            logInteraction(imposterRequest, NO_MATCH, false)
            responseWriter.write(NO_MATCH, httpResponse)
        }
    }

    void configure(HttpServletRequest httpRequest) {

        Map json = gson.fromJson(httpRequest.inputStream.text, HashMap)
        
        ImposterRequest imposterRequest = requestFactory.fromJson(json.request)
        ImposterResponse imposterResponse = responseFactory.fromJson(json.response)

        map.put(imposterRequest, imposterResponse)
    }

    void put(ImposterRequest imposterRequest, ImposterResponse imposterResponse) {
        map[imposterRequest] = imposterResponse
    }

    ImposterResponse get(ImposterRequest imposterRequest) {
        return map[imposterRequest]
    }

    boolean hasUnmatched() {
        return unmatched > 0
    }

    void reset() {
        map.clear()
        unmatched = 0
    }

    private void logInteraction(ImposterRequest imposterRequest, ImposterResponse imposterResponse, boolean matched) {

        log.info matched ? '\n>> [Http Imposter]: Matched Request' : '\n>> [Http Imposter]: Unmatched Request'
        log.info '>> ==================================='
        log.info imposterRequest.toString()
        log.info '>>'

        log.info '\n>> [Http Imposter]: Sending Response'
        log.info '>> ==================================='
        log.info imposterResponse.toString()
        log.info '>>'
    }
}

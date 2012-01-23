package net.xelnaga.httpimposter

import groovy.util.logging.Log
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.ImposterRequest
import net.xelnaga.httpimposter.model.ImposterResponse
import net.xelnaga.httpimposter.factory.ImposterRequestFactory
import net.xelnaga.httpimposter.factory.ImposterResponseFactory

@Log
class HttpImposter {

    static final NO_MATCH = new ImposterResponse(
            status: HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
            headers: [
                    new HttpHeader('Content-Type', 'text/plain')
            ],
            body: 'No match found for http request'
    )

    ImposterRequestFactory requestReader = new ImposterRequestFactory()
    ImposterResponseWriter responseWriter = new ImposterResponseWriter()

    private Map<ImposterRequest, ImposterResponse> map

    HttpImposter() {
        map = [:]
    }

    void setFilter(HttpHeaderFilter filter) {
        requestReader = new ImposterRequestFactory(filter: filter)
    }

    void respond(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        ImposterRequest imposterRequest = requestReader.fromHttpRequest(httpRequest)
        ImposterResponse imposterResponse = map.get(imposterRequest)

        if (imposterResponse) {
            logInteraction(imposterRequest, imposterResponse, true)
            responseWriter.write(imposterResponse, httpResponse)
        } else {
            logInteraction(imposterRequest, NO_MATCH, false)
            responseWriter.write(NO_MATCH, httpResponse)
        }
    }

    void configure(HttpServletRequest httpRequest) {

        ImposterRequestFactory requestFactory = new ImposterRequestFactory()
        ImposterResponseFactory responseFactory = new ImposterResponseFactory()

        ImposterRequest imposterRequest = requestFactory.fromJson(httpRequest.getParameter('imposterRequest'))
        ImposterResponse imposterResponse = responseFactory.fromJson(httpRequest.getParameter('imposterResponse'))

        map.put(imposterRequest, imposterResponse)
    }

    void put(ImposterRequest imposterRequest, ImposterResponse imposterResponse) {
        map[imposterRequest] = imposterResponse
    }

    ImposterResponse get(ImposterRequest imposterRequest) {
        return map[imposterRequest]
    }

    void reset() {
        map.clear()
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

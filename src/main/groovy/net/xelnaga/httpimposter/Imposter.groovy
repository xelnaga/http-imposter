package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.ImposterRequest
import net.xelnaga.httpimposter.model.ImposterResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import groovy.util.logging.Log

@Log
class Imposter {

    static final NO_MATCH = new ImposterResponse(
            status: 500,
            headers: [
                    'Content-Type': 'text/plain'
            ],
            body: 'No match found for http request'
    )
    
    ImposterRequestReader requestReader = new ImposterRequestReader()
    ImposterResponseWriter responseWriter = new ImposterResponseWriter()

    private Map<ImposterRequest, ImposterResponse> map
 
    Imposter() {
        map = [:]
    }

    void respond(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        ImposterRequest imposterRequest = requestReader.read(httpRequest)
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

        ImposterJsonParser parser = new ImposterJsonParser()

        ImposterRequest imposterRequest = parser.parseRequest(httpRequest.getParameter('imposterRequest'))
        ImposterResponse imposterResponse = parser.parseResponse(httpRequest.getParameter('imposterResponse'))

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

package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.ImposterRequest
import net.xelnaga.httpimposter.model.ImposterResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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
            logMatch(imposterRequest, imposterResponse, true)
            responseWriter.write(imposterResponse, httpResponse)
        } else {
            logMatch(imposterRequest, NO_MATCH, false)
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

    private void logMatch(ImposterRequest imposterRequest, ImposterResponse imposterResponse, boolean matched) {

        println matched ? '>> [Http Imposter]: Matched Request' : '>> [Http Imposter]: Unmatched Request'
        println '>> ==================================='
        print imposterRequest.toString()
        println '>>'

        println '>> [Http Imposter]: Sending Response'
        println '>> ==================================='
        print imposterResponse.toString()
        println '>>'
    }
}

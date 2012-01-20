package net.xelnaga.httpimposter

import javax.servlet.http.HttpServletRequest
import net.xelnaga.httpimposter.model.ImposterRequest

class ImposterRequestReader {

    ImposterRequest read(HttpServletRequest httpRequest) {

        ImposterRequest imposterRequest = new ImposterRequest(
                uri:    httpRequest.requestURI,
                method: httpRequest.method,
                body:   httpRequest.inputStream.text)
        
        httpRequest.headerNames.each { String name ->
            
            String value = httpRequest.getHeader(name)

            name = name.capitalize()
            if (name != 'Host') {
                imposterRequest.headers[name] = value
            }
        }
        
        return imposterRequest
    }
}

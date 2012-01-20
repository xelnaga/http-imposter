package net.xelnaga.httpimposter

import javax.servlet.http.HttpServletRequest
import net.xelnaga.httpimposter.model.ImposterRequest
import groovy.json.JsonSlurper

class ImposterRequestReader {

    ImposterRequest read(HttpServletRequest httpRequest) {

        ImposterRequest imposterRequest = new ImposterRequest(
                uri:    httpRequest.requestURI,
                method: httpRequest.method,
                body:   httpRequest.inputStream.text)
        
        httpRequest.headerNames.each { String name ->
            imposterRequest.headers[name] = httpRequest.getHeader(name)
        }
        
        return imposterRequest
    }
}

package net.xelnaga.httpimposter

import javax.servlet.http.HttpServletRequest

class ImposterFactory {

    ImposterRequest makeRequest(HttpServletRequest httpRequest) {
        
        String body = httpRequest.inputStream.text
        
        return new ImposterRequest(body:  body)
    }
}

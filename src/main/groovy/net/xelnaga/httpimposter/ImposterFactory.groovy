package net.xelnaga.httpimposter

import javax.servlet.http.HttpServletRequest

class ImposterFactory {

    ImposterRequest makeRequest(HttpServletRequest httpRequest) {
        
        String requestBody = httpRequest.inputStream.text
        
        return new ImposterRequest(requestBody: requestBody)
    }
}

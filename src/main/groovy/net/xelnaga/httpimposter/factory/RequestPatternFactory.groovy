package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.filter.PassThroughFilter
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.RequestPattern

import javax.servlet.http.HttpServletRequest

class RequestPatternFactory {

    HttpHeaderFilter filter = new PassThroughFilter()

    RequestPattern fromHttpRequest(HttpServletRequest httpRequest) {

        RequestPattern requestPattern = new RequestPattern(
                uri:    httpRequest.requestURI,
                method: httpRequest.method,
                body:   httpRequest.inputStream.text)
        
        httpRequest.headerNames.each { String name ->

            String value = httpRequest.getHeader(name)
            HttpHeader httpHeader = new HttpHeader(name, value)
            addMatchableHeader(requestPattern, httpHeader)
        }
        
        return requestPattern
    }

    private void addMatchableHeader(RequestPattern requestPattern, HttpHeader httpHeader) {

        if (filter.isMatchable(httpHeader)) {
            requestPattern.headers << httpHeader
        }
    }
}

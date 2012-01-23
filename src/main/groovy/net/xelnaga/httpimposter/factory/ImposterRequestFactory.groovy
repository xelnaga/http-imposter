package net.xelnaga.httpimposter.factory

import javax.servlet.http.HttpServletRequest
import net.xelnaga.httpimposter.model.ImposterRequest
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.filter.PassThroughFilter
import groovy.json.JsonSlurper

class ImposterRequestFactory {

    HttpHeaderFilter filter = new PassThroughFilter()

    ImposterRequest fromHttpRequest(HttpServletRequest httpRequest) {

        ImposterRequest imposterRequest = new ImposterRequest(
                uri:    httpRequest.requestURI,
                method: httpRequest.method,
                body:   httpRequest.inputStream.text)
        
        httpRequest.headerNames.each { String name ->

            String value = httpRequest.getHeader(name)
            HttpHeader httpHeader = new HttpHeader(name, value)
            addMatchableHeader(imposterRequest, httpHeader)
        }
        
        return imposterRequest
    }

    ImposterRequest fromJson(String json) {

        def result = new JsonSlurper().parseText(json)

        ImposterRequest imposterRequest = new ImposterRequest(
                uri: result.uri,
                method: result.method,
                body: result.body
        )

        result.headers.each { Map header ->
            
            HttpHeader httpHeader = new HttpHeader(header.name, header.value)
            addMatchableHeader(imposterRequest, httpHeader)
        }

        return imposterRequest
    }
    
    private void addMatchableHeader(ImposterRequest imposterRequest, HttpHeader httpHeader) {

        if (filter.isMatchable(httpHeader)) {
            imposterRequest.headers << httpHeader
        }
    }
}

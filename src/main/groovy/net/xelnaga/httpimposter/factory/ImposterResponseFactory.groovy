package net.xelnaga.httpimposter.factory

import groovy.json.JsonSlurper
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.ImposterResponse
import org.apache.commons.codec.binary.Base64

class ImposterResponseFactory {

    ImposterResponse fromJson(String json) {
        
        def result = new JsonSlurper().parseText(json)
        
        ImposterResponse imposterResponse = new ImposterResponse(
                status: result.status,
                body: new String(Base64.decodeBase64((String) result.body))
        )

        result.headers.each { Map header ->
            imposterResponse.headers << new HttpHeader(header.name, header.value)
        }

        return imposterResponse
    }
}
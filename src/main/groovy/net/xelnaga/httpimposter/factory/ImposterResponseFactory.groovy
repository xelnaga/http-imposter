package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.ImposterResponse
import org.apache.commons.codec.binary.Base64

class ImposterResponseFactory {

    ImposterResponse fromJson(Map json) {
        
        ImposterResponse imposterResponse = new ImposterResponse(
                status: json.status,
                body: new String(Base64.decodeBase64((String) json.body))
        )

        json.headers.each { Map header ->
            imposterResponse.headers << new HttpHeader(header.name, header.value)
        }

        return imposterResponse
    }
}
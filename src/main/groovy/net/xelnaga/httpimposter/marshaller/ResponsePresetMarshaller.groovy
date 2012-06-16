package net.xelnaga.httpimposter.marshaller

import net.xelnaga.httpimposter.model.HttpHeader

import org.apache.commons.codec.binary.Base64
import net.xelnaga.httpimposter.model.ResponsePreset

class ResponsePresetMarshaller {

    ResponsePreset fromJson(Map json) {
        
        ResponsePreset imposterResponse = new ResponsePreset(
                status: json.status,
                body: new String(Base64.decodeBase64((String) json.body))
        )

        json.headers.each { Map header ->
            imposterResponse.headers << new HttpHeader(header.name, header.value)
        }

        return imposterResponse
    }
}
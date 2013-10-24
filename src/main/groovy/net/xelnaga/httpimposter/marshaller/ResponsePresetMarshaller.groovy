package net.xelnaga.httpimposter.marshaller

import net.xelnaga.httpimposter.factory.HttpHeaderFactory
import net.xelnaga.httpimposter.factory.TypeResolvingHeaderFactory
import net.xelnaga.httpimposter.model.ByteArrayResponsePreset
import net.xelnaga.httpimposter.model.ResponsePreset
import org.apache.commons.codec.binary.Base64

class ResponsePresetMarshaller {
    HttpHeaderFactory httpHeaderFactory = new TypeResolvingHeaderFactory()

    ResponsePreset fromJson(Map json) {

        ResponsePreset imposterResponse = makeResponsePresetFromType(json)

        json.headers.each { Map header ->
            imposterResponse.headers << httpHeaderFactory.makeHeader(header)
        }

        return imposterResponse
    }

    private ResponsePreset makeResponsePresetFromType(Map json) {

        if (json.type == 'ByteArray') {
            return makeByteArrayResponsePreset(json)
        }

        return makeStringResponsePreset(json)
    }

    private ResponsePreset makeStringResponsePreset(Map json) {

        ResponsePreset imposterResponse = new ResponsePreset(
                status: json.status,
                body: new String(Base64.decodeBase64((String) json.body))
        )

        return imposterResponse
    }

    private ResponsePreset makeByteArrayResponsePreset(Map json) {

        ResponsePreset imposterResponse = new ByteArrayResponsePreset(
                status: json.status,
                body: json.body
        )

        return imposterResponse
    }

}
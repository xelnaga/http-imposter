package net.xelnaga.httpimposter.model

import org.apache.commons.codec.binary.Base64

class ByteArrayResponsePreset extends ResponsePreset {

    byte[] getBodyAsByteArray() {

        return Base64.decodeBase64(body)
    }

    String getEncodedBody() {

        return body
    }
}
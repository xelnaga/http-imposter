package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode
import org.apache.commons.codec.binary.Base64

@EqualsAndHashCode
class ResponsePreset {

    int status
    Set<HttpHeader> headers = [] as TreeSet
    String body

    byte[] getBodyAsByteArray() {

        return body.bytes
    }

    String getEncodedBody() {

        return new String(Base64.encodeBase64(getBodyAsByteArray()))
    }
}

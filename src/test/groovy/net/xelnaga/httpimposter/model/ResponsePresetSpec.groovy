package net.xelnaga.httpimposter.model

import spock.lang.Specification

class ResponsePresetSpec extends Specification {

    def 'create instance'() {

        given:
            Set<HttpHeader> expectedHeaders = [Mock(HttpHeader)] as Set
            int expectedStatus = 200
            Body expectedRequestBody = new DefaultBody('body')

        when:
            ResponsePreset responsePreset = new ResponsePreset(body: expectedRequestBody, status: expectedStatus, headers: expectedHeaders)

        then:
            responsePreset.status == expectedStatus
            responsePreset.headers == expectedHeaders
            responsePreset.body == expectedRequestBody
    }
}

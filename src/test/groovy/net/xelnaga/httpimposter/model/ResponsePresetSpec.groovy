package net.xelnaga.httpimposter.model

import spock.lang.Specification

class ResponsePresetSpec extends Specification {

    def 'get encoded body'() {

        given:
            ResponsePreset responsePreset = new ResponsePreset(body: 'bodytest64')

        expect:
            responsePreset.encodedBody == 'Ym9keXRlc3Q2NA=='

        and:
            responsePreset.bodyAsByteArray == 'bodytest64'.bytes
    }


}

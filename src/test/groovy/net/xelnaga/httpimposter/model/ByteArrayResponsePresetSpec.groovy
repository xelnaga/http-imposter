package net.xelnaga.httpimposter.model

import spock.lang.Specification

class ByteArrayResponsePresetSpec extends Specification {

    def 'get encoded body'() {

        given:
            ResponsePreset responsePreset = new ByteArrayResponsePreset(body: 'Ym9keXRlc3Q2NA==')

        expect:
            responsePreset.encodedBody == 'Ym9keXRlc3Q2NA=='

        and:
            responsePreset.bodyAsByteArray == 'bodytest64'.bytes
    }
}

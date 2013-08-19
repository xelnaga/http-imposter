package net.xelnaga.httpimposter.marshaller

import net.xelnaga.httpimposter.model.ByteArrayResponsePreset
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.ResponsePreset
import spock.lang.Specification

class ResponsePresetMarshallerSpec extends Specification {

    private ResponsePresetMarshaller marshaller
    
    void setup() {
        marshaller = new ResponsePresetMarshaller()
    }

    def 'from json'() {
        
        given:
            Map json = [
                    headers: [
                            [name: 'Content-Type', value: 'text/exciting'],
                            [name: 'Lemon', value: 'Lime']
                    ],
                    status: 234,
                    body: 'Ym9keXRlc3Q2NA=='
            ]

        when:
            ResponsePreset result = marshaller.fromJson(json)

        then:
            result == new ResponsePreset(
                    status: 234,
                    headers: [
                            new HttpHeader('Content-Type', 'text/exciting'),
                            new HttpHeader('Lemon', 'Lime')
                    ],
                    body: 'bodytest64'
            )

        and:
            result instanceof ResponsePreset
    }

    def 'from json when byte array'() {

        given:
            Map json = [
                    headers: [
                            [name: 'Content-Type', value: 'text/exciting'],
                            [name: 'Lemon', value: 'Lime']
                    ],
                    status: 234,
                    type: 'ByteArray',
                    body: 'Ym9keXRlc3Q2NA=='
            ]

        when:
            ResponsePreset result = marshaller.fromJson(json)

        then:
            result == new ResponsePreset(
                    status: 234,
                    headers: [
                            new HttpHeader('Content-Type', 'text/exciting'),
                            new HttpHeader('Lemon', 'Lime')
                    ],
                    body: 'Ym9keXRlc3Q2NA=='
            )

        and:
            result instanceof ByteArrayResponsePreset
    }
}

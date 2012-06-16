package net.xelnaga.httpimposter.marshaller

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
        
        expect:
            marshaller.fromJson(json) == new ResponsePreset(
                    status: 234,
                    headers: [
                            new HttpHeader('Content-Type', 'text/exciting'),
                            new HttpHeader('Lemon', 'Lime')
                    ],
                    body: 'bodytest64'
            )
    }
}

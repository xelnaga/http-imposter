package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.ImposterResponse
import spock.lang.Specification

class ImposterResponseFactorySpec extends Specification {

    private ImposterResponseFactory factory
    
    void setup() {
        factory = new ImposterResponseFactory()
    }

    def 'parse response'() {
        
        given:
            Map jsonMap = [ headers: [
                        [name: 'Content-Type', value: 'text/exciting'],
                        [name: 'Lemon', value: 'Lime']
                    ],
                    status: 234,
                    body: 'Ym9keXRlc3Q2NA=='
            ]
        
        expect:
            factory.fromJson(jsonMap) == new ImposterResponse(
                    status: 234,
                    headers: [
                            new HttpHeader('Content-Type', 'text/exciting'),
                            new HttpHeader('Lemon', 'Lime')
                    ],
                    body: 'bodytest64'
            )
    }
}

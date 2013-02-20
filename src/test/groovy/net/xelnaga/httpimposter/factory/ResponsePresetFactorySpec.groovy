package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.ResponsePreset
import spock.lang.Specification

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR

class ResponsePresetFactorySpec extends Specification {

    ResponsePresetFactory factory

    void setup() {
        factory = new ResponsePresetFactory()
    }

    def 'make unexpected'() {

        when:
            ResponsePreset responsePreset = factory.makeUnexpected()

        then:
            responsePreset == new ResponsePreset(
                    status: SC_INTERNAL_SERVER_ERROR,
                    headers: [
                            new HttpHeader('Content-Type', 'text/plain')
                    ],
                    body: 'UNEXPECTED REQUEST PATTERN'
            )
    }
}

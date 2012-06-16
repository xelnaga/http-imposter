package net.xelnaga.httpimposter.factory

import spock.lang.Specification
import net.xelnaga.httpimposter.model.ResponsePreset

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import net.xelnaga.httpimposter.model.HttpHeader

class ResponsePresetFactorySpec extends Specification {

    ResponsePresetFactory factory

    void setup() {
        factory = new ResponsePresetFactory()
    }

    def 'make unmatched'() {

        when:
            ResponsePreset responsePreset = factory.makeUnmatched()

        then:
            responsePreset == new ResponsePreset(
                    status: SC_INTERNAL_SERVER_ERROR,
                    headers: [
                            new HttpHeader('Content-Type', 'text/plain')
                    ],
                    body: 'No match found for http request'
            )
    }
}

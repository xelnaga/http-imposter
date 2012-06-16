package net.xelnaga.httpimposter

import spock.lang.Specification
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset
import net.xelnaga.httpimposter.factory.ResponsePresetFactory

class EngineSpec extends Specification {

    Engine engine

    void setup() {
        engine = new Engine()
    }

    def 'interact when expected'() {

        given:
            RequestPattern requestPattern = new RequestPattern(body: 'qwerty')
            ResponsePreset responsePreset = new ResponsePreset(body: 'asdfgh')

        when:
            engine.expect(requestPattern, responsePreset)

        then:
            engine.interact(requestPattern) == responsePreset
    }

    def 'interact when not expected'() {

        given:
            RequestPattern requestPattern = new RequestPattern(body: 'qwerty')

        expect:
            engine.interact(requestPattern) == new ResponsePresetFactory().makeUnmatched()
    }
}

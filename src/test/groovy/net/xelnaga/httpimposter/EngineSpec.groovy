package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.factory.ResponsePresetFactory
import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset
import spock.lang.Specification

class EngineSpec extends Specification {

    Engine engine

    void setup() {
        engine = new Engine()
    }

    def 'expect, interact, verify and reset after multiple expected and unexpected interactions'() {

        given:
            RequestPattern requestPattern1 = new RequestPattern(body: 'qwerty')
            RequestPattern requestPattern2 = new RequestPattern(body: 'mmndfd')
            RequestPattern requestPattern3 = new RequestPattern(body: 'erwrwr')

            ResponsePreset responsePreset1 = new ResponsePreset(body: 'asdfgh')
            ResponsePreset responsePreset2 = new ResponsePreset(body: 'gshshs')
            ResponsePreset responsePreset3 = new ResponsePresetFactory().makeUnexpected()

        expect:
            engine.expectations == []
            engine.interactions == []

        when:
            engine.expect(3, requestPattern1, responsePreset1)
            engine.expect(2, requestPattern2, responsePreset2)

        then:
            engine.expectations == [
                new Interaction(requestPattern: requestPattern1, responsePreset: responsePreset1, expected: 3, actual: 0),
                new Interaction(requestPattern: requestPattern2, responsePreset: responsePreset2, expected: 2, actual: 0),
            ]
            engine.interactions == []

        when:
            ResponsePreset result1 = engine.interact(requestPattern1)

        then:
            result1 == responsePreset1
            engine.interactions == [ requestPattern1 ]

        when:
            ResponsePreset result2 = engine.interact(requestPattern3)

        then:
            result2 == responsePreset3
            engine.interactions == [ requestPattern1, requestPattern3 ]

        when:
            ResponsePreset result3 = engine.interact(requestPattern2)

        then:
            result3 == responsePreset2
            engine.interactions == [ requestPattern1, requestPattern3, requestPattern2 ]

        when:
            ResponsePreset result4 = engine.interact(requestPattern1)

        then:
            result4 == responsePreset1
            engine.interactions == [ requestPattern1, requestPattern3, requestPattern2, requestPattern1 ]

        when:
            List<Interaction> expectations = engine.expectations

        then:
            expectations == [
                    new Interaction(requestPattern: requestPattern1, responsePreset: responsePreset1, expected: 3, actual: 2),
                    new Interaction(requestPattern: requestPattern2, responsePreset: responsePreset2, expected: 2, actual: 1),
                    new Interaction(requestPattern: requestPattern3, responsePreset: responsePreset3, expected: 0, actual: 1)
            ]

        when:
            engine.reset()

        then:
            engine.expectations == []
            engine.interactions == []
    }
}

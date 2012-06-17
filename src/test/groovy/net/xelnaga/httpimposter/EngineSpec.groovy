package net.xelnaga.httpimposter

import spock.lang.Specification
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset
import net.xelnaga.httpimposter.factory.ResponsePresetFactory
import net.xelnaga.httpimposter.model.Interaction

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
            engine.verify() == []

        when:
            engine.expect(3, requestPattern1, responsePreset1)
            engine.expect(2, requestPattern2, responsePreset2)

        then:
            engine.verify() == [
                new Interaction(requestPattern: requestPattern1, responsePreset: responsePreset1, expected: 3, actual: 0),
                new Interaction(requestPattern: requestPattern2, responsePreset: responsePreset2, expected: 2, actual: 0),
            ]

        and:
            engine.interact(requestPattern1) == responsePreset1
            engine.interact(requestPattern3) == responsePreset3
            engine.interact(requestPattern2) == responsePreset2
            engine.interact(requestPattern1) == responsePreset1

        when:
            List<Interaction> interactions = engine.verify()

        then:
            interactions == [
                    new Interaction(requestPattern: requestPattern1, responsePreset: responsePreset1, expected: 3, actual: 2),
                    new Interaction(requestPattern: requestPattern2, responsePreset: responsePreset2, expected: 2, actual: 1),
                    new Interaction(requestPattern: requestPattern3, responsePreset: responsePreset3, expected: 0, actual: 1)
            ]

        when:
            engine.reset()

        then:
            engine.verify() == []
    }
}

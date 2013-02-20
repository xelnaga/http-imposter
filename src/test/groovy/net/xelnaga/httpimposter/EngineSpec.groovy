package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.factory.ResponsePresetFactory
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset
import net.xelnaga.httpimposter.transport.Report
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
            engine.report == new Report(
                expectations: [],
                interactions: []
            )

        when:
            engine.expect(3, requestPattern1, responsePreset1)
            engine.expect(2, requestPattern2, responsePreset2)

        then:
            engine.report == new Report(
                    expectations: [
                            requestPattern1,
                            requestPattern1,
                            requestPattern1,
                            requestPattern2,
                            requestPattern2
                    ],
                    interactions: [

                    ]
            )

        when:
            ResponsePreset result1 = engine.interact(requestPattern1)

        then:
            result1 == responsePreset1

        when:
            ResponsePreset result2 = engine.interact(requestPattern3)

        then:
            result2 == responsePreset3

        when:
            ResponsePreset result3 = engine.interact(requestPattern2)

        then:
            result3 == responsePreset2

        when:
            ResponsePreset result4 = engine.interact(requestPattern1)

        then:
            result4 == responsePreset1

        and:


        then:
            engine.report == new Report(
                    expectations: [
                            requestPattern1,
                            requestPattern1,
                            requestPattern1,
                            requestPattern2,
                            requestPattern2
                    ],
                    interactions: [
                            requestPattern1,
                            requestPattern3,
                            requestPattern2,
                            requestPattern1
                    ],


            )

        when:
            engine.reset()

        then:
            engine.report == new Report(
                    expectations: [],
                    interactions: []
            )
    }
}

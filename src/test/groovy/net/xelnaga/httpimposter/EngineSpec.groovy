package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.model.Report
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset
import spock.lang.Specification

class EngineSpec extends Specification {

    Engine engine

    SpecificationHelper helper

    ResponseProvider mockResponseProvider

    void setup() {

        engine = new Engine()

        helper = new SpecificationHelper()

        mockResponseProvider = Mock(ResponseProvider)
        engine.responseProvider = mockResponseProvider
    }

    def 'expect'() {

        given:
            Interaction expectation = helper.makeInteraction(1)

        when:
            engine.expect(expectation)

        then:
            1 * mockResponseProvider.add(expectation)
            0 * _._

        and:
            engine.report == new Report([expectation], [])
    }

    def 'interact'() {

        given:
            RequestPattern request = helper.makeRequestPattern(1)
            ResponsePreset response = helper.makeResponsePreset(1)
            Interaction interaction = helper.makeInteraction(1)

        when:
            Interaction result = engine.interact(request)

        then:
            1 * mockResponseProvider.get(request) >> response
            0 * _._

        and:
            result == new Interaction(request, response)

        and:
            engine.report == new Report([], [interaction])
    }

    def 'reset'() {

        given:
            Interaction interaction = helper.makeInteraction(1)

        when:
            engine.expect(interaction)
            engine.interact(interaction.request)

        then:
            1 * mockResponseProvider.add(interaction)
        then:
            1 * mockResponseProvider.get(interaction.request) >> interaction.response
            0 * _._

        then:
            engine.report == new Report([interaction],[interaction])

        when:
            engine.reset()

        then:
            1 * mockResponseProvider.reset()
            0 * _._

        and:
            engine.report == new Report([],[])
    }
}

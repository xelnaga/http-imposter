package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.factory.ResponsePresetFactory
import net.xelnaga.httpimposter.model.DefaultHttpHeader
import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.model.RegexMatchingHttpHeader
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset
import spock.lang.Specification

class MappedResponseProviderSpec extends Specification {

    ResponseProvider responseProvider

    ResponsePresetFactory mockResponsePresetFactory

    void setup() {

        responseProvider = new MappedResponseProvider()

        mockResponsePresetFactory = Mock(ResponsePresetFactory)
        responseProvider.responsePresetFactory = mockResponsePresetFactory
    }

    def 'get with known request pattern'() {

        given:
            RequestPattern request = Mock(RequestPattern)
            ResponsePreset response = Mock(ResponsePreset)

            Interaction interaction = new Interaction(request, response)

        and:
            responseProvider.add(interaction)

        when:
            ResponsePreset result = responseProvider.get(request)

        then:
            1 * request.matches(request) >> true
            0 * _

        and:
            result.is(response)
    }

    def 'get with request pattern should match when using regex header matching implementation'() {

        given:
            RequestPattern expectedRequest = new RequestPattern(
                headers: [
                    new RegexMatchingHttpHeader('name', '.*matchthis.*')
                ]
            )
            ResponsePreset response = Mock(ResponsePreset)

            Interaction interaction = new Interaction(expectedRequest, response)

        and:
            responseProvider.add(interaction)

        and:
            RequestPattern actualRequest = new RequestPattern(
                headers: [
                    new DefaultHttpHeader('name', '---matchthis---')
                ]
            )

        when:
            ResponsePreset result = responseProvider.get(actualRequest)

        then:
            0 * _

        and:
            result.is(response)
    }


    def 'get with unknown request pattern'() {

        given:
            RequestPattern request = Mock(RequestPattern)
            ResponsePreset response = Mock(ResponsePreset)

        when:
            ResponsePreset result = responseProvider.get(request)

        then:
            1 * mockResponsePresetFactory.makeUnexpected() >> response
            0 * _

        and:
            result.is(response)
    }

    def 'second interaction overwrites first if request is the same'() {

        given:
            RequestPattern request = Mock(RequestPattern)

            ResponsePreset response1 = Mock(ResponsePreset)
            ResponsePreset response2 = Mock(ResponsePreset)

            Interaction interaction1 = new Interaction(request, response1)
            Interaction interaction2 = new Interaction(request, response2)

        and:
            responseProvider.add(interaction1)
            responseProvider.add(interaction2)

        when:
            responseProvider.get(request).is(response2)

        then:
            1 * request.matches(request) >> true
            0 * _

    }

    def 'reset'() {

        given:
            RequestPattern request = Mock(RequestPattern)
            ResponsePreset response = Mock(ResponsePreset)

            Interaction interaction = new Interaction(request, response)

        and:
            responseProvider.add(interaction)

        when:
            responseProvider.get(request).is(response)

        then:
            1 * request.matches(request) >> true
            0 * _

        when:
            responseProvider.reset()

        then:
            0 * _

        and:
            responseProvider.get(request) == null
    }
}

package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.factory.RequestPatternFactory
import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.model.Report
import net.xelnaga.httpimposter.model.Body
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset
import net.xelnaga.httpimposter.serializer.InteractionSerializer
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HttpImposterSpec extends Specification {

    HttpImposter httpImposter

    RequestPatternFactory mockRequestPatternFactory

    Engine mockEngine

    ResponseWriter mockResponseWriter
    InteractionSerializer mockInteractionSerializer

    void setup() {

        httpImposter = new HttpImposter()

        mockRequestPatternFactory = Mock(RequestPatternFactory)
        httpImposter.requestPatternFactory = mockRequestPatternFactory

        mockEngine = Mock(Engine)
        httpImposter.engine = mockEngine

        mockResponseWriter = Mock(ResponseWriter)
        httpImposter.responseWriter = mockResponseWriter

        mockInteractionSerializer = Mock(InteractionSerializer)
        httpImposter.interactionSerializer = mockInteractionSerializer
    }

    def 'set filter'() {

        given:
            HttpHeaderFilter filter = Mock(HttpHeaderFilter)

        when:
            httpImposter.setFilter(filter)

        then:
            1 * mockRequestPatternFactory.setProperty('filter', filter)

        then:
            0 * _
        }

    def 'expect'() {
        
        given:
            String content = '{}'
            HttpServletRequest httpRequest = new MockHttpServletRequest()
            httpRequest.content = content.bytes
            Interaction mockInteraction = Mock(Interaction)

        when:
            httpImposter.expect(httpRequest)

        then:
            1 * mockInteractionSerializer.deserialize(content) >> mockInteraction
            1 * mockEngine.expect(mockInteraction)
            0 * _
    }

    def 'interact'() {
    
        given:
            HttpServletRequest httpRequest = new MockHttpServletRequest(content: 'apple'.bytes)
            HttpServletResponse httpResponse = new MockHttpServletResponse()

            RequestPattern requestPattern = new RequestPattern(body: Mock(Body))
            ResponsePreset responsePreset = new ResponsePreset(status: 234, body: Mock(Body))
            Interaction interaction = new Interaction(requestPattern, responsePreset)

        when:
            httpImposter.interact(httpRequest, httpResponse)

        then:
            1 * mockRequestPatternFactory.fromHttpRequest(httpRequest) >> requestPattern
        then:
            1 * mockEngine.interact(requestPattern) >> interaction
        then:
            1 * mockResponseWriter.write(responsePreset, httpResponse)
            0 * _
    }

    def 'report'() {

        given:
            HttpServletResponse response = new MockHttpServletResponse()
            Report mockReport = Mock(Report)

        when:
            httpImposter.report(response)

        then:
            1 * mockEngine.getReport() >> mockReport
        then:
            1 * mockResponseWriter.write(mockReport, response)
            0 * _
    }

    def 'reset'() {

        when:
            httpImposter.reset()

        then:
            1 * mockEngine.reset()
            0 * _
    }
}

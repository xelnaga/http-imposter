package net.xelnaga.httpimposter

import com.google.gson.Gson
import net.xelnaga.httpimposter.factory.RequestPatternFactory
import net.xelnaga.httpimposter.filter.HeaderNameExclusionFilter
import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.marshaller.RequestPatternMarshaller
import net.xelnaga.httpimposter.marshaller.ResponsePresetMarshaller
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HttpImposterSpec extends Specification {

    HttpImposter httpImposter

    RequestPatternFactory mockRequestPatternFactory
    RequestPatternMarshaller mockRequestPatternMarshaller
    ResponsePresetMarshaller mockResponsePresetMarshaller

    Engine mockEngine

    LogWriter mockLogWriter
    ResponseWriter mockResponseWriter

    void setup() {

        httpImposter = new HttpImposter()

        mockRequestPatternFactory = Mock(RequestPatternFactory)
        httpImposter.requestPatternFactory = mockRequestPatternFactory

        mockRequestPatternMarshaller = Mock(RequestPatternMarshaller)
        httpImposter.requestPatternMarshaller = mockRequestPatternMarshaller

        mockResponsePresetMarshaller = Mock(ResponsePresetMarshaller)
        httpImposter.responsePresetMarshaller = mockResponsePresetMarshaller

        mockEngine = Mock(Engine)
        httpImposter.engine = mockEngine

        mockLogWriter = Mock(LogWriter)
        httpImposter.logWriter = mockLogWriter

        mockResponseWriter = Mock(ResponseWriter)
        httpImposter.responseWriter = mockResponseWriter
    }

    def 'set filter'() {

        given:
            HttpHeaderFilter filter = new HeaderNameExclusionFilter(['qwerty'])

        when:
            httpImposter.setFilter(filter)

        then:
            1 * mockRequestPatternFactory.setProperty('filter', filter)
            1 * mockRequestPatternMarshaller.setProperty('filter', filter)
            0 * _._
    }

    def 'expect'() {
        
        given:
            HttpServletRequest httpRequest = new MockHttpServletRequest()
            httpRequest.content = new Gson().toJson([cardinality: 4, requestPattern: [qwerty: 'qwerty'], responsePreset: [asdfgh: 'asdfgh']]).bytes

            RequestPattern requestPattern = new RequestPattern(body: 'apple')
            ResponsePreset responsePreset = new ResponsePreset(body: 'pear')

        when:
            httpImposter.expect(httpRequest)

        then:
            1 * mockRequestPatternMarshaller.fromJson([qwerty: 'qwerty']) >> requestPattern
            1 * mockResponsePresetMarshaller.fromJson([asdfgh: 'asdfgh']) >> responsePreset
            1 * mockEngine.expect(4, requestPattern, responsePreset)
            0 * _._
    }

    def 'interact'() {
    
        given:
            HttpServletRequest httpRequest = new MockHttpServletRequest(content: 'apple'.bytes)
            HttpServletResponse httpResponse = new MockHttpServletResponse()

            RequestPattern requestPattern = new RequestPattern(body: 'hello')
            ResponsePreset responsePreset = new ResponsePreset(status: 234, body: 'pear')
        

        when:
            httpImposter.interact(httpRequest, httpResponse)

        then:
            1 * mockRequestPatternFactory.fromHttpRequest(httpRequest) >> requestPattern
            1 * mockEngine.interact(requestPattern) >> responsePreset
            1 * mockLogWriter.interact(requestPattern, responsePreset)
            1 * mockResponseWriter.write(responsePreset, httpResponse)
            0 *_._
    }
    
    def 'reset'() {

        when:
            httpImposter.reset()

        then:
            1 * mockEngine.reset()
            0 * _._
    }
}

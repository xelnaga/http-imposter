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

    void setup() {

        httpImposter = new HttpImposter()

        mockRequestPatternFactory = Mock(RequestPatternFactory)
        httpImposter.requestPatternFactory = mockRequestPatternFactory

        mockRequestPatternMarshaller = Mock(RequestPatternMarshaller)
        httpImposter.requestPatternMarshaller = mockRequestPatternMarshaller

        mockResponsePresetMarshaller = Mock(ResponsePresetMarshaller)
        httpImposter.responsePresetMarshaller = mockResponsePresetMarshaller
    }

    def 'get when mapping exists'() {

        given:
            RequestPattern requestPattern = new RequestPattern(body: 'hello')
            ResponsePreset responsePreset = new ResponsePreset(body: 'world')

        when:
            httpImposter.put(requestPattern, responsePreset)

        then:
            httpImposter.get(requestPattern) == responsePreset
    }

    def 'get when mapping does not exist'() {
    
        given:
            RequestPattern requestPattern = new RequestPattern(body: 'hello')
        
        expect:
            httpImposter.get(requestPattern) == null
    }
   
    def 'configure'() {
        
        given:
            HttpServletRequest httpRequest = new MockHttpServletRequest()
            httpRequest.content = new Gson().toJson([request: [qwerty: 'qwerty'], response: [asdfgh: 'asdfgh']]).bytes

            RequestPattern requestPattern = new RequestPattern(body: 'apple')
            ResponsePreset responsePreset = new ResponsePreset(body: 'pear')

        when:
            httpImposter.configure(httpRequest)

        then:
            (1) * mockRequestPatternMarshaller.fromJson([qwerty: 'qwerty']) >> requestPattern
            (1) * mockResponsePresetMarshaller.fromJson([asdfgh: 'asdfgh']) >> responsePreset
            (0) * _._

        and:
            httpImposter.get(requestPattern) == responsePreset
    }

    def 'respond when match'() {
    
        given:
            HttpServletRequest httpRequest = new MockHttpServletRequest(content: 'apple'.bytes)
            HttpServletResponse httpResponse = new MockHttpServletResponse()

            RequestPattern requestPattern = new RequestPattern(body: 'hello')
            ResponsePreset responsePreset = new ResponsePreset(status: 234, body: 'pear')
        
            httpImposter.put(requestPattern, responsePreset)
        
        when:
            httpImposter.respond(httpRequest, httpResponse)

        then:
            (1) * mockRequestPatternFactory.fromHttpRequest(httpRequest) >> requestPattern
            (0) *_._

        and:
            httpResponse.status == 234
            httpResponse.contentAsString == 'pear'
            !httpImposter.hasUnmatched()
    }
    
    def 'respond when no match'() {

        given:
            HttpServletRequest httpRequest = new MockHttpServletRequest(content: 'apple'.bytes)
            HttpServletResponse httpResponse = new MockHttpServletResponse()

            RequestPattern requestPattern = new RequestPattern(body: 'hello')

        when:
            httpImposter.respond(httpRequest, httpResponse)

        then:
            (1) * mockRequestPatternFactory.fromHttpRequest(httpRequest) >> requestPattern
            (0) * _._

        and:
            httpResponse.status == HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            httpResponse.getHeader('Content-Type') == 'text/plain'
            httpResponse.contentAsString == 'No match found for http request'
            httpImposter.hasUnmatched()
    }
    
    def 'reset'() {

        given:
            RequestPattern requestPattern = new RequestPattern(body: 'hello')
            ResponsePreset responsePreset = new ResponsePreset(body: 'world')
            httpImposter.put(requestPattern, responsePreset)

        when:
            httpImposter.reset()

        then:
            (0) * _._

        and:
            httpImposter.get(requestPattern) == null
    }
    
    def 'set filter'() {
        
        given:
            HttpHeaderFilter filter = new HeaderNameExclusionFilter(['qwerty'])
        
        when:
            httpImposter.setFilter(filter)

        then:
            (0) * _._

        and:
            httpImposter.requestPatternFactory.filter.is(filter)
    }
}

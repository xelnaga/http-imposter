package net.xelnaga.httpimposter

import com.google.gson.Gson
import net.xelnaga.httpimposter.factory.ImposterRequestFactory
import net.xelnaga.httpimposter.factory.ImposterResponseFactory
import net.xelnaga.httpimposter.filter.HeaderNameExclusionFilter
import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.model.ImposterRequest
import net.xelnaga.httpimposter.model.ImposterResponse
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HttpImposterSpec extends Specification {

    HttpImposter httpImposter

    ImposterRequestFactory mockImposterRequestFactory
    ImposterResponseFactory mockImposterResponseFactory

    void setup() {

        httpImposter = new HttpImposter()

        mockImposterRequestFactory = Mock(ImposterRequestFactory)
        httpImposter.requestFactory = mockImposterRequestFactory

        mockImposterResponseFactory = Mock(ImposterResponseFactory)
        httpImposter.responseFactory = mockImposterResponseFactory
    }

    def 'get when mapping exists'() {

        given:
            ImposterRequest request = new ImposterRequest(body: 'hello')
            ImposterResponse response = new ImposterResponse(body: 'world')

        when:
            httpImposter.put(request, response)

        then:
            httpImposter.get(request) == response
    }

    def 'get when mapping does not exist'() {
    
        given:
            ImposterRequest request = new ImposterRequest(body: 'hello')
        
        expect:
            httpImposter.get(request) == null
    }
   
    def 'configure'() {
        
        given:
            HttpServletRequest httpRequest = new MockHttpServletRequest()
            httpRequest.content = new Gson().toJson([request: [qwerty: 'qwerty'], response: [asdfgh: 'asdfgh']]).bytes

            ImposterRequest imposterRequest = new ImposterRequest(body: 'apple')
            ImposterResponse imposterResponse = new ImposterResponse(body: 'pear')

        when:
            httpImposter.configure(httpRequest)

        then:
            (1) * mockImposterRequestFactory.fromJson([qwerty: 'qwerty']) >> imposterRequest
            (1) * mockImposterResponseFactory.fromJson([asdfgh: 'asdfgh']) >> imposterResponse
            (0) * _._

        and:
            httpImposter.get(imposterRequest) == imposterResponse
    }

    def 'respond when match'() {
    
        given:
            HttpServletRequest httpRequest = new MockHttpServletRequest(content: 'apple'.bytes)
            HttpServletResponse httpResponse = new MockHttpServletResponse()

            ImposterRequest imposterRequest = new ImposterRequest(body: 'hello')
            ImposterResponse imposterResponse = new ImposterResponse(status: 234, body: 'pear')
        
            httpImposter.put(imposterRequest, imposterResponse)
        
        when:
            httpImposter.respond(httpRequest, httpResponse)

        then:
            (1) * mockImposterRequestFactory.fromHttpRequest(httpRequest) >> imposterRequest
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

            ImposterRequest imposterRequest = new ImposterRequest(body: 'hello')

        when:
            httpImposter.respond(httpRequest, httpResponse)

        then:
            (1) * mockImposterRequestFactory.fromHttpRequest(httpRequest) >> imposterRequest
            (0) * _._

        and:
            httpResponse.status == HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            httpResponse.getHeader('Content-Type') == 'text/plain'
            httpResponse.contentAsString == 'No match found for http request'
            httpImposter.hasUnmatched()
    }
    
    def 'reset'() {

        given:
            ImposterRequest request = new ImposterRequest(body: 'hello')
            ImposterResponse response = new ImposterResponse(body: 'world')
            httpImposter.put(request, response)

        when:
            httpImposter.reset()

        then:
            (0) * _._

        and:
            httpImposter.get(request) == null
    }
    
    def 'set filter'() {
        
        given:
            HttpHeaderFilter filter = new HeaderNameExclusionFilter(['qwerty'])
        
        when:
            httpImposter.setFilter(filter)

        then:
            (0) * _._

        and:
            httpImposter.requestFactory.filter.is(filter)
    }
}

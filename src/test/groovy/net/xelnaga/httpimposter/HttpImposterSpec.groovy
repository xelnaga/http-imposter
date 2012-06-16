package net.xelnaga.httpimposter

import spock.lang.Specification

import net.xelnaga.httpimposter.model.ImposterResponse
import javax.servlet.http.HttpServletRequest
import org.springframework.mock.web.MockHttpServletRequest
import org.gmock.WithGMock
import org.springframework.mock.web.MockHttpServletResponse
import javax.servlet.http.HttpServletResponse

import net.xelnaga.httpimposter.model.ImposterRequest
import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.filter.HeaderNameExclusionFilter
import net.xelnaga.httpimposter.factory.ImposterRequestFactory
import net.xelnaga.httpimposter.factory.ImposterResponseFactory
import com.google.gson.Gson

@WithGMock
class HttpImposterSpec extends Specification {

    private HttpImposter httpImposter
    
    void setup() {
        httpImposter = new HttpImposter()
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
            httpRequest.content = '{ "some": "json" }'.bytes

            Gson mockJsonSlurper = mock(Gson, constructor())
            mockJsonSlurper.fromJson('{ "some": "json" }', HashMap).returns([ request: 'qwerty', response: 'asdfgh' ])
        
            ImposterRequest imposterRequest = new ImposterRequest(body: 'apple')
            ImposterResponse imposterResponse = new ImposterResponse(body: 'pear')
            
            ImposterRequestFactory mockImposterRequestFactory = mock(ImposterRequestFactory, constructor())
            mockImposterRequestFactory.fromJson('qwerty').returns(imposterRequest)

            ImposterResponseFactory mockImposterResponseFactory = mock(ImposterResponseFactory, constructor())
            mockImposterResponseFactory.fromJson('asdfgh').returns(imposterResponse)
        
        when:
            play {
                httpImposter.configure(httpRequest)
            }

        then:
            httpImposter.get(imposterRequest) == imposterResponse
    }

    def 'respond when match'() {
    
        given:
            HttpServletRequest httpRequest = new MockHttpServletRequest()
            httpRequest.content = 'apple'.bytes

            HttpServletResponse httpResponse = new MockHttpServletResponse()

            ImposterRequest imposterRequest = new ImposterRequestFactory().fromHttpRequest(httpRequest)
            ImposterResponse imposterResponse = new ImposterResponse(status: 234, body: 'pear')
        
            httpImposter.put(imposterRequest, imposterResponse)
        
        when:
            play {
                httpImposter.respond(httpRequest, httpResponse)    
            }
        
        then:
            httpResponse.status == 234
            httpResponse.contentAsString == 'pear'
            !httpImposter.hasUnmatched()
    }
    
    def 'respond when no match'() {

        given:
            HttpServletRequest httpRequest = new MockHttpServletRequest()
            httpRequest.content = 'apple'.bytes

            HttpServletResponse httpResponse = new MockHttpServletResponse()

        when:
            httpImposter.respond(httpRequest, httpResponse)

        then:
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
            httpImposter.get(request) == null
    }
    
    def 'set filter'() {
        
        given:
            HttpHeaderFilter filter = new HeaderNameExclusionFilter([ 'qwerty' ])
        
        when:
            httpImposter.setFilter(filter)

        then:
            httpImposter.requestReader.filter.is(filter)
    }
}

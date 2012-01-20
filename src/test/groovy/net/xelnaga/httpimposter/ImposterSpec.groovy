package net.xelnaga.httpimposter

import spock.lang.Specification
import net.xelnaga.httpimposter.model.ImposterRequest
import net.xelnaga.httpimposter.model.ImposterResponse
import javax.servlet.http.HttpServletRequest
import org.springframework.mock.web.MockHttpServletRequest
import org.gmock.WithGMock
import org.springframework.mock.web.MockHttpServletResponse
import javax.servlet.http.HttpServletResponse

@WithGMock
class ImposterSpec extends Specification {

    private Imposter imposter
    
    void setup() {
        imposter = new Imposter()
    }

    def 'get when mapping exists'() {

        given:
            ImposterRequest request = new ImposterRequest(body: 'hello')
            ImposterResponse response = new ImposterResponse(body: 'world')

        when:
            imposter.put(request, response)

        then:
            imposter.get(request) == response
    }

    def 'get when mapping does not exist'() {
    
        given:
            ImposterRequest request = new ImposterRequest(body: 'hello')
        
        expect:
            imposter.get(request) == null
    }

    
    
    def 'configure'() {
        
        given:
            HttpServletRequest httpRequest = new MockHttpServletRequest()
            httpRequest.addParameter('imposterRequest', 'qwerty')
            httpRequest.addParameter('imposterResponse', 'asdfgh')
        
            ImposterRequest imposterRequest = new ImposterRequest(body: 'apple')
            ImposterResponse imposterResponse = new ImposterResponse(body: 'pear')
        
            ImposterJsonParser mockImposterJsonParser = mock(ImposterJsonParser, constructor())
            mockImposterJsonParser.parseRequest('qwerty').returns(imposterRequest)
            mockImposterJsonParser.parseResponse('asdfgh').returns(imposterResponse)
        
        when:
            play {
                imposter.configure(httpRequest)
            }

        then:
            imposter.get(imposterRequest) == imposterResponse
    }

    def 'respond when match'() {
    
        given:
            HttpServletRequest httpRequest = new MockHttpServletRequest()
            httpRequest.content = 'apple'.bytes

            HttpServletResponse httpResponse = new MockHttpServletResponse()

            ImposterRequest imposterRequest = new ImposterRequestReader().read(httpRequest)
            ImposterResponse imposterResponse = new ImposterResponse(status: 234, body: 'pear')
        
            imposter.put(imposterRequest, imposterResponse)
        
        when:
            play {
                imposter.respond(httpRequest, httpResponse)    
            }
        
        then:
            httpResponse.status == 234
            httpResponse.contentAsString == 'pear'
    }
    
    def 'respond when no match'() {

        given:
            HttpServletRequest httpRequest = new MockHttpServletRequest()
            httpRequest.content = 'apple'.bytes

            HttpServletResponse httpResponse = new MockHttpServletResponse()

        when:
            play {
                imposter.respond(httpRequest, httpResponse)
            }

        then:
            httpResponse.status == 500
            httpResponse.contentType == 'text/plain'
            httpResponse.contentAsString == 'No match found for http request'
    }
    
    def 'reset'() {

        given:
        ImposterRequest request = new ImposterRequest(body: 'hello')
        ImposterResponse response = new ImposterResponse(body: 'world')
        imposter.put(request, response)

        when:
        imposter.reset()

        then:
        imposter.get(request) == null
    }
}

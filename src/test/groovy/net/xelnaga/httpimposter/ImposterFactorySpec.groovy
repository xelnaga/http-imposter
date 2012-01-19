package net.xelnaga.httpimposter

import javax.servlet.http.HttpServletRequest
import org.gmock.WithGMock
import spock.lang.Specification

@WithGMock
class ImposterFactorySpec extends Specification {
    
    private ImposterFactory factory
    
    void setup() {
        factory = new ImposterFactory()
    }
    
    def 'make request'() {
        
        given:
            InputStream inputStream = new ByteArrayInputStream('qwerty'.bytes)
            HttpServletRequest mockHttpRequest = mock(HttpServletRequest)
            mockHttpRequest.getInputStream().returns(inputStream)
        
        when:
            ImposterRequest request = null
            play {
                request = factory.makeRequest(mockHttpRequest)
            }
        
        then:
            request.body == 'qwerty'
    }
}

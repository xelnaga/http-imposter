package net.xelnaga.httpimposter

import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification
import net.xelnaga.httpimposter.model.ImposterRequest

class ImposterRequestReaderSpec extends Specification {
    
    private ImposterRequestReader reader
    
    void setup() {
        reader = new ImposterRequestReader()
    }
    
    def 'read'() {
        
        given:
            MockHttpServletRequest httpRequest = new MockHttpServletRequest()
            httpRequest.contentType = 'text/banana'
            httpRequest.content = 'qwerty'.bytes

        when:
            ImposterRequest imposterRequest = reader.read(httpRequest)

        then:
            imposterRequest.contentType == 'text/banana'
            imposterRequest.requestBody == 'qwerty'
    }
}

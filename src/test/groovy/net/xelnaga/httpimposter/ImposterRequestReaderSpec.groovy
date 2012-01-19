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
            MockHttpServletRequest httpRequest = new MockHttpServletRequest(
                    requestURI: '/fruity/pineapple',
                    method: 'mango',
                    contentType: 'text/banana',
                    content: 'qwerty'.bytes
             )

        when:
            ImposterRequest imposterRequest = reader.read(httpRequest)

        then:
            imposterRequest == new ImposterRequest(
                    uri: '/fruity/pineapple',
                    method: 'mango',
                    mime: 'text/banana',
                    body: 'qwerty'
            )
    }
}

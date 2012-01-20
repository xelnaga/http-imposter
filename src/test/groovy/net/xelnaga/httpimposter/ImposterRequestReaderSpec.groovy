package net.xelnaga.httpimposter

import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification
import net.xelnaga.httpimposter.model.ImposterRequest
import groovy.json.JsonOutput

class ImposterRequestReaderSpec extends Specification {
    
    private ImposterRequestReader reader
    
    void setup() {
        reader = new ImposterRequestReader()
    }
    
    def 'read from http request'() {
        
        given:
            MockHttpServletRequest httpRequest = new MockHttpServletRequest(
                    requestURI: '/fruity/pineapple',
                    method: 'mango',
                    contentType: 'text/banana',
                    content: 'qwerty'.bytes
            )
            httpRequest.addHeader('Pineapple', 'Passionfruit')
            httpRequest.addHeader('Host', 'somehost')

        when:
            ImposterRequest imposterRequest = reader.read(httpRequest)

        then:
            imposterRequest == new ImposterRequest(
                    uri: '/fruity/pineapple',
                    method: 'mango',
                    headers: [
                            'Content-Type': 'text/banana',
                            'Pineapple': 'Passionfruit'
                    ],
                    body: 'qwerty'
            )
    }
}

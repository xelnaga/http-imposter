package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.ResponsePreset
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

class ImposterResponseWriterSpec extends Specification {

    private ResponseWriter writer
    
    void setup() {
        writer = new ResponseWriter()
    }
    
    def 'write'() {
        
        given:
            ResponsePreset responsePreset = new ResponsePreset(
                    status: 234,
                    headers: [
                        new HttpHeader('Content-Type', 'text/exciting'),
                        new HttpHeader('Lemon', 'Lime')
                    ],
                    body: 'qwertyuiop'
            )

            MockHttpServletResponse httpResponse = new MockHttpServletResponse()
        
        when:
            writer.write(responsePreset, httpResponse)
        
        then:
            httpResponse.status == 234
            httpResponse.contentType == 'text/exciting'
            httpResponse.getHeader('Lemon') == 'Lime'
            httpResponse.contentAsString == 'qwertyuiop'
    }
}

package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.ResponsePreset
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification
import net.xelnaga.httpimposter.model.Interaction
import javax.servlet.http.HttpServletResponse

class ImposterResponseWriterSpec extends Specification {

    private ResponseWriter writer
    
    void setup() {
        writer = new ResponseWriter()
    }
    
    def 'write response preset'() {
        
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

    def 'write interactions'() {

        given:
            List<Interaction> interactions = [ new Interaction(expected: 2) ]
            MockHttpServletResponse httpResponse = new MockHttpServletResponse()

        when:
            writer.write(interactions, httpResponse)

        then:
            httpResponse.status == HttpServletResponse.SC_OK
            httpResponse.contentType == 'application/json'
            httpResponse.contentAsString == '[{"expected":2}]'
    }
}

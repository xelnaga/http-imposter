package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

import static javax.servlet.http.HttpServletResponse.SC_OK

class ResponseWriterSpec extends Specification {

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
            List<RequestPattern> interactions = [ new RequestPattern(body: 'hello'), new RequestPattern(body: 'world') ]
            List<Interaction> expectations = [ new Interaction(expected: 3), new Interaction(expected: 2) ]
            MockHttpServletResponse httpResponse = new MockHttpServletResponse()

        when:
            writer.write(interactions, expectations, httpResponse)

        then:
            httpResponse.status == SC_OK
            httpResponse.contentType == 'application/json'
            httpResponse.contentAsString == '{"expectations":[{"expected":3},{"expected":2}],"interactions":[{"headers":[],"body":"hello"},{"headers":[],"body":"world"}]}'
    }
}

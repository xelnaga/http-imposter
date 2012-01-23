package net.xelnaga.httpimposter

import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification
import net.xelnaga.httpimposter.model.ImposterResponse
import net.xelnaga.httpimposter.model.HttpHeader

class ImposterResponseWriterSpec extends Specification {

    private ImposterResponseWriter writer
    
    void setup() {
        writer = new ImposterResponseWriter()
    }
    
    def 'write'() {
        
        given:
            ImposterResponse imposterResponse = new ImposterResponse(
                    status: 234,
                    headers: [
                        new HttpHeader('Content-Type', 'text/exciting'),
                        new HttpHeader('Lemon', 'Lime')
                    ],
                    body: 'qwertyuiop'
            )

            MockHttpServletResponse httpResponse = new MockHttpServletResponse()
        
        when:
            writer.write(imposterResponse, httpResponse)
        
        then:
            httpResponse.status == 234
            httpResponse.contentType == 'text/exciting'
            httpResponse.getHeader('Lemon') == 'Lime'
            httpResponse.contentAsString == 'qwertyuiop'
    }
}

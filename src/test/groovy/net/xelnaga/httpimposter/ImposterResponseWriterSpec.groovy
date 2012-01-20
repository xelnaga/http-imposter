package net.xelnaga.httpimposter

import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification
import net.xelnaga.httpimposter.model.ImposterResponse

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
                        'Content-Type': 'text/exciting',
                        'Lemon': 'Lime'
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

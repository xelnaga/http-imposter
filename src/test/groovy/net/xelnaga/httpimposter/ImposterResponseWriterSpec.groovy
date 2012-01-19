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
                    statusCode: 234,
                    contentType: 'text/exciting',
                    responseBody: 'qwertyuiop'
            )

            MockHttpServletResponse httpResponse = new MockHttpServletResponse()
        
        when:
            writer.write(imposterResponse, httpResponse)
        
        then:
            httpResponse.status == 234
            httpResponse.contentType == 'text/exciting'
            httpResponse.contentAsString == 'qwertyuiop'
    }
}

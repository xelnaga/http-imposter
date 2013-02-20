package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.ResponsePreset
import net.xelnaga.httpimposter.serialiser.ReportSerializer
import net.xelnaga.httpimposter.model.Report
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

import static javax.servlet.http.HttpServletResponse.SC_OK

class ResponseWriterSpec extends Specification {

    ResponseWriter writer

    ReportSerializer mockReportSerializer

    void setup() {

        writer = new ResponseWriter()

        mockReportSerializer = Mock(ReportSerializer)
        writer.reportSerializer = mockReportSerializer
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

    def 'write report'() {

        given:
            Report mockReport = Mock(Report)
            MockHttpServletResponse mockResponse = new MockHttpServletResponse()

        when:
            writer.write(mockReport, mockResponse)

        then:
            1 * mockReportSerializer.serialize(mockReport) >> 'abcdef'
            0 * _._

        and:
            mockResponse.status == SC_OK
            mockResponse.contentType == 'application/json'
            mockResponse.contentAsString == 'abcdef'
    }
}

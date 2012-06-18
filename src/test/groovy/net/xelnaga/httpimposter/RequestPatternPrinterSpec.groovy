package net.xelnaga.httpimposter

import spock.lang.Specification
import net.xelnaga.httpimposter.printer.RequestPatternPrinter
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.HttpHeader

class RequestPatternPrinterSpec extends Specification {

    RequestPatternPrinter printer

    void setup() {
        printer = new RequestPatternPrinter()
    }

    private RequestPattern createSampleRequestPattern(String body) {
        return new RequestPattern(
                uri: '/hello/world',
                method: 'POST',
                headers: [
                        new HttpHeader('Accept', 'application/json'),
                        new HttpHeader('Content-Type', 'text/xml')
                ],
                body:  body
        )
    }

    def 'print'() {

        given:
            RequestPattern requestPattern = createSampleRequestPattern('<xml></xml>')

        when:
            String result = printer.print(requestPattern)

        then:
            result == """POST /hello/world

Content-Type: text/xml
Accept: application/json

<xml></xml>
"""
    }

    def 'print without body'() {

        given:
        RequestPattern requestPattern = createSampleRequestPattern('')

        when:
        String result = printer.print(requestPattern)

        then:
        result == """POST /hello/world

Content-Type: text/xml
Accept: application/json

(No request body)
"""
    }
}

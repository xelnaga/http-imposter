package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.ResponsePreset
import spock.lang.Specification
import net.xelnaga.httpimposter.printer.RequestPatternPrinter
import net.xelnaga.httpimposter.printer.ResponsePresetPrinter

class ResponsePresetPrinterSpec extends Specification {
    
    private ResponsePresetPrinter printer
    
    void setup() {
        printer = new ResponsePresetPrinter()
    }

    private ResponsePreset createSampleResponsePreset(String body) {
        return new ResponsePreset(
                status: 456,
                headers: [
                        new HttpHeader('Content-Type', 'text/plain'),
                        new HttpHeader('Content-Length', '69')
                ],
                body: body
        )
    }

    def 'print'() {
        given:
            ResponsePreset responsePreset = createSampleResponsePreset('Hello World')

        expect:
            printer.print(responsePreset) == '''HTTP/1.1 456

Content-Length: 69
Content-Type: text/plain

Hello World
'''
    }

    def 'print without body'() {
        given:
            ResponsePreset responsePreset = createSampleResponsePreset('')

        expect:
            printer.print(responsePreset) == '''HTTP/1.1 456

Content-Length: 69
Content-Type: text/plain

(No response body)
'''
    }
}

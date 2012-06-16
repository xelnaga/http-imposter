package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset
import spock.lang.Specification

class ImposterPrinterSpec extends Specification {
    
    private StringPrinter printer
    
    void setup() {
        printer = new StringPrinter()
    }
    
    def 'print request pattern'() {
        
        given:
            RequestPattern requestPattern = new RequestPattern(
                    uri: '/some/uri',
                    method: 'POST',
                    headers: [
                            new HttpHeader('Content-Type', 'text/plain'),
                            new HttpHeader('Content-Length', '69')
                    ],
                    body: 'Hello World')
        
        expect:
            printer.print(requestPattern) == '''POST /some/uri

Content-Length: 69
Content-Type: text/plain

Hello World
'''
    }
    
    def 'print response preset'() {
        
        given:
            ResponsePreset responsePreset = new ResponsePreset(
                    status: 456,
                    headers: [
                            new HttpHeader('Content-Type', 'text/plain'),
                            new HttpHeader('Content-Length', '69')
                    ],
                    body: 'Hello World')        

        expect:
            printer.print(responsePreset) == '''HTTP/1.1 456

Content-Length: 69
Content-Type: text/plain

Hello World
'''
    }
}

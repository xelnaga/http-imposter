package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.ImposterRequest
import net.xelnaga.httpimposter.model.ImposterResponse
import spock.lang.Specification

class ImposterPrinterSpec extends Specification {
    
    private ImposterPrinter printer
    
    void setup() {
        printer = new ImposterPrinter()    
    }
    
    def 'print imposter request'() {
        
        given:
            ImposterRequest imposterRequest = new ImposterRequest(
                    uri: '/some/uri',
                    method: 'POST',
                    headers: [
                            new HttpHeader('Content-Type', 'text/plain'),
                            new HttpHeader('Content-Length', '69')
                    ],
                    body: 'Hello World')
        
        expect:
            printer.print(imposterRequest) == '''POST /some/uri

Content-Length: 69
Content-Type: text/plain

Hello World
'''
    }
    
    def 'print imposter response'() {
        
        given:
            ImposterResponse imposterResponse = new ImposterResponse(
                    status: 456,
                    headers: [
                            new HttpHeader('Content-Type', 'text/plain'),
                            new HttpHeader('Content-Length', '69')
                    ],
                    body: 'Hello World')        

        expect:
            printer.print(imposterResponse) == '''HTTP/1.1 456

Content-Length: 69
Content-Type: text/plain

Hello World
'''
    }
}

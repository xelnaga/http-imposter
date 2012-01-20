package net.xelnaga.httpimposter

import spock.lang.Specification
import net.xelnaga.httpimposter.model.ImposterRequest
import net.xelnaga.httpimposter.model.ImposterResponse

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
                            'Content-Type': 'text/plain',
                            'Content-Length': '69'
                    ],
                    body: 'Hello World')
        
        expect:
            printer.print(imposterRequest) == '''POST /some/uri

Content-Type: text/plain
Content-Length: 69

Hello World
'''
    }
    
    def 'print imposter response'() {
        
        given:
            ImposterResponse imposterResponse = new ImposterResponse(
                    status: 456,
                    headers: [
                            'Content-Type': 'text/plain',
                            'Content-Length': '69'
                    ],
                    body: 'Hello World')        

        expect:
            printer.print(imposterResponse) == '''HTTP/1.1 456

Content-Type: text/plain
Content-Length: 69

Hello World
'''
    }
}

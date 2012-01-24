package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.ImposterResponse
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.ImposterRequest

class StringPrinter {

    String print(ImposterRequest imposterRequest) {

        String output = "${imposterRequest.method} ${imposterRequest.uri}\n\n"
        output += printHeaders(imposterRequest.headers)
        output += "\n\n${imposterRequest.body}\n"

        return output
    }

    String print(ImposterResponse imposterResponse) {

        String output = "HTTP/1.1 ${imposterResponse.status}\n\n"
        output += printHeaders(imposterResponse.headers)
        output += "\n\n${imposterResponse.body}\n"

        return output
    }
    
    private String printHeaders(Set<HttpHeader> headers) {

        return headers.collect { HttpHeader httpHeader ->
            httpHeader.toString()
        }.join('\n')
    }
}

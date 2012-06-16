package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset

class StringPrinter {

    String print(RequestPattern requestPattern) {

        String output = "${requestPattern.method} ${requestPattern.uri}\n\n"
        output += printHeaders(requestPattern.headers)
        output += "\n\n${requestPattern.body}\n"

        return output
    }

    String print(ResponsePreset responsePreset) {

        String output = "HTTP/1.1 ${responsePreset.status}\n\n"
        output += printHeaders(responsePreset.headers)
        output += "\n\n${responsePreset.body}\n"

        return output
    }
    
    private String printHeaders(Set<HttpHeader> headers) {

        return headers.collect { HttpHeader httpHeader ->
            httpHeader.toString()
        }.join('\n')
    }
}

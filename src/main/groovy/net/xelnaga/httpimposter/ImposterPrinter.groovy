package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.ImposterRequest
import net.xelnaga.httpimposter.model.ImposterResponse

class ImposterPrinter {

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
    
    private String printHeaders(Map<String, String> headers) {

        return headers.collect { String name, String value ->
            "${name}: ${value}"
        }.join('\n')
    }
}

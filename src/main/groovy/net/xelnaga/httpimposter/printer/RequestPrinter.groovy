package net.xelnaga.httpimposter.printer

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.RequestPattern

class RequestPrinter {

    String print(RequestPattern request) {

        String output = request.method + ' ' + request.uri + '\n'

        if (request.headers.size() > 0) {
            output += '\n'
            request.headers.each { HttpHeader header ->
                output += header.toString() + '\n'
            }
        }

        if (!request.body.isEmpty()) {
            output += '\n'
            output += "type: '" + request.body.type + "'\n"
            output += request.body.value + '\n'
        }

        return output
    }
}

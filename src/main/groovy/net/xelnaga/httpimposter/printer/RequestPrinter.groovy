package net.xelnaga.httpimposter.printer

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.RequestPattern

class RequestPrinter {

    String print(RequestPattern pattern) {

        String output = pattern.method + ' ' + pattern.uri + '\n'

        if (pattern.headers.size() > 0) {
            output += '\n'
            pattern.headers.each { HttpHeader header ->
                output += header.name.toLowerCase() + ': ' + header.value + '\n'
            }
        }

        if (pattern.body.size() > 0) {
            output += '\n'
            output += pattern.body + '\n'
        }

        return output
    }
}

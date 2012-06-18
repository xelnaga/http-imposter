package net.xelnaga.httpimposter.printer

import net.xelnaga.httpimposter.model.RequestPattern

class RequestPatternPrinter {

    String print(RequestPattern requestPattern) {

        String output = "${requestPattern.method} ${requestPattern.uri}\n\n"
        output += requestPattern.headers*.toString().join('\n')
        output += "\n\n${requestPattern.body ?: '(No request body)'}\n"

        return output
    }
}

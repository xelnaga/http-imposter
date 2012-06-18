package net.xelnaga.httpimposter.printer

import net.xelnaga.httpimposter.model.ResponsePreset

class ResponsePresetPrinter {

    String print(ResponsePreset responsePreset) {

        String output = "HTTP/1.1 ${responsePreset.status}\n\n"
        output += responsePreset.headers*.toString().join('\n')
        output += "\n\n${responsePreset.body ?: '(No response body)'}\n"

        return output
    }
}

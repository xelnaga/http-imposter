package net.xelnaga.httpimposter

import javax.servlet.http.HttpServletResponse

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.ResponsePreset

class ResponseWriter {

    HttpServletResponse write(ResponsePreset responsePreset, HttpServletResponse httpResponse) {

        httpResponse.status = responsePreset.status

        responsePreset.headers.each { HttpHeader httpHeader ->
            httpResponse.addHeader(httpHeader.name, httpHeader.value)
        }

        httpResponse.outputStream << responsePreset.body

        return httpResponse
    }
}

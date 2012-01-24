package net.xelnaga.httpimposter

import javax.servlet.http.HttpServletResponse
import net.xelnaga.httpimposter.model.ImposterResponse
import net.xelnaga.httpimposter.model.HttpHeader

class ResponseWriter {

    HttpServletResponse write(ImposterResponse imposterResponse, HttpServletResponse httpResponse) {

        httpResponse.status = imposterResponse.status

        imposterResponse.headers.each { HttpHeader httpHeader ->
            httpResponse.addHeader(httpHeader.name, httpHeader.value)
        }

        httpResponse.outputStream << imposterResponse.body

        return httpResponse
    }
}

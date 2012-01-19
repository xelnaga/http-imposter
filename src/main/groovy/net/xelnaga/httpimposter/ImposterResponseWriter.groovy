package net.xelnaga.httpimposter

import javax.servlet.http.HttpServletResponse
import net.xelnaga.httpimposter.model.ImposterResponse

class ImposterResponseWriter {

    HttpServletResponse write(ImposterResponse imposterResponse, HttpServletResponse httpResponse) {

        httpResponse.status = imposterResponse.statusCode
        httpResponse.contentType = imposterResponse.contentType
        httpResponse.outputStream << imposterResponse.responseBody

        return httpResponse
    }
}

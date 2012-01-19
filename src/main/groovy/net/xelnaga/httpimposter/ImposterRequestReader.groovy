package net.xelnaga.httpimposter

import javax.servlet.http.HttpServletRequest
import net.xelnaga.httpimposter.model.ImposterRequest

class ImposterRequestReader {

    ImposterRequest read(HttpServletRequest httpRequest) {

        return new ImposterRequest(
                contentType: httpRequest.contentType,
                requestBody: httpRequest.inputStream.text)
    }
}

package net.xelnaga.httpimposter

import javax.servlet.http.HttpServletRequest
import net.xelnaga.httpimposter.model.ImposterRequest

class ImposterRequestReader {

    ImposterRequest read(HttpServletRequest httpRequest) {

        return new ImposterRequest(
                uri:    httpRequest.requestURI,
                method: httpRequest.method,
                mime:   httpRequest.contentType,
                body:   httpRequest.inputStream.text)
    }
}

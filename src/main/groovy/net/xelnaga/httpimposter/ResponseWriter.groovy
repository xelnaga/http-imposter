package net.xelnaga.httpimposter

import javax.servlet.http.HttpServletResponse

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.ResponsePreset
import net.xelnaga.httpimposter.model.Interaction
import com.google.gson.Gson

class ResponseWriter {

    Gson gson = new Gson()

    HttpServletResponse write(ResponsePreset responsePreset, HttpServletResponse httpResponse) {

        httpResponse.status = responsePreset.status

        responsePreset.headers.each { HttpHeader httpHeader ->
            httpResponse.addHeader(httpHeader.name, httpHeader.value)
        }

        httpResponse.outputStream << responsePreset.body

        return httpResponse
    }

    HttpServletResponse write(List<Interaction> interactions, HttpServletResponse httpResponse) {

        httpResponse.setContentType('application/json')
        httpResponse.outputStream << gson.toJson(interactions)

        return httpResponse
    }
}

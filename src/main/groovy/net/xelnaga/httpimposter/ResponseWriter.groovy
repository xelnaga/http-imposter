package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.transport.Report
import net.xelnaga.httpimposter.model.RequestPattern

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

    HttpServletResponse write(List<RequestPattern> interactions, List<Interaction> expectations, HttpServletResponse httpResponse) {

        Report report = new Report(
                expectations: expectations,
                interactions: interactions,
        )

        httpResponse.setContentType('application/json')
        httpResponse.outputStream << gson.toJson(report)

        return httpResponse
    }
}

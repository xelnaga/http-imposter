package net.xelnaga.httpimposter

import com.google.gson.Gson
import net.xelnaga.httpimposter.factory.RequestPatternFactory
import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.marshaller.RequestPatternMarshaller
import net.xelnaga.httpimposter.marshaller.ResponsePresetMarshaller
import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.model.Report
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HttpImposter {

    Gson gson = new Gson()

    RequestPatternFactory requestPatternFactory = new RequestPatternFactory()
    RequestPatternMarshaller requestPatternMarshaller = new RequestPatternMarshaller()
    ResponsePresetMarshaller responsePresetMarshaller = new ResponsePresetMarshaller()

    Engine engine = new Engine()

    ResponseWriter responseWriter = new ResponseWriter()

    void setFilter(HttpHeaderFilter filter) {

        requestPatternFactory.filter = filter
        requestPatternMarshaller.filter = filter
    }

    void expect(HttpServletRequest httpRequest) {

        Map json = gson.fromJson(httpRequest.inputStream.text, HashMap)

        RequestPattern requestPattern = requestPatternMarshaller.fromJson(json.requestPattern)
        ResponsePreset responsePreset = responsePresetMarshaller.fromJson(json.responsePreset)
        Interaction interaction = new Interaction(requestPattern, responsePreset)

        engine.expect(interaction)
    }

    void interact(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        RequestPattern requestPattern = requestPatternFactory.fromHttpRequest(httpRequest)
        Interaction interaction = engine.interact(requestPattern)

        responseWriter.write(interaction.response, httpResponse)
    }

    void report(HttpServletResponse response) {

        Report report = engine.report

        responseWriter.write(report, response)
    }

    void reset() {
        engine.reset()
    }
}

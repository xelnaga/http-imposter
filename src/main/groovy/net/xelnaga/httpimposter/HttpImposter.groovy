package net.xelnaga.httpimposter

import com.google.gson.Gson
import net.xelnaga.httpimposter.factory.RequestPatternFactory
import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.marshaller.RequestPatternMarshaller
import net.xelnaga.httpimposter.marshaller.ResponsePresetMarshaller
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import net.xelnaga.httpimposter.model.Interaction

class HttpImposter {

    Gson gson = new Gson()

    RequestPatternFactory requestPatternFactory = new RequestPatternFactory()
    RequestPatternMarshaller requestPatternMarshaller = new RequestPatternMarshaller()
    ResponsePresetMarshaller responsePresetMarshaller = new ResponsePresetMarshaller()

    Engine engine = new Engine()

    LogWriter logWriter = new LogWriter()
    ResponseWriter responseWriter = new ResponseWriter()

    void setFilter(HttpHeaderFilter filter) {

        requestPatternFactory.filter = filter
        requestPatternMarshaller.filter = filter
    }

    void expect(HttpServletRequest httpRequest) {

        Map json = gson.fromJson(httpRequest.inputStream.text, HashMap)

        int cardinality = json.cardinality
        RequestPattern requestPattern = requestPatternMarshaller.fromJson(json.requestPattern)
        ResponsePreset responsePreset = responsePresetMarshaller.fromJson(json.responsePreset)

        engine.expect(cardinality, requestPattern, responsePreset)
    }

    void interact(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        RequestPattern requestPattern = requestPatternFactory.fromHttpRequest(httpRequest)
        ResponsePreset responsePreset = engine.interact(requestPattern)

        logWriter.interact(requestPattern, responsePreset)
        responseWriter.write(responsePreset, httpResponse)
    }

    void verify(HttpServletResponse httpResponse) {

        List<Interaction> interactions = engine.verify()

    }

    void reset() {
        engine.reset()
    }
}

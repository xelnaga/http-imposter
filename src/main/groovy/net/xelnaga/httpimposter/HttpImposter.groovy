package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.factory.RequestPatternFactory
import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.model.Report
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.serializer.InteractionSerializer
import net.xelnaga.httpimposter.serializer.json.JsonInteractionSerializer

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HttpImposter {

    RequestPatternFactory requestPatternFactory = new RequestPatternFactory()

    Engine engine = new Engine()

    ResponseWriter responseWriter = new ResponseWriter()
    InteractionSerializer interactionSerializer = new JsonInteractionSerializer()

    void setFilter(HttpHeaderFilter filter) {

        requestPatternFactory.filter = filter
    }

    void expect(HttpServletRequest httpRequest) {

        Interaction interaction = interactionSerializer.deserialize(httpRequest.inputStream.text)
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

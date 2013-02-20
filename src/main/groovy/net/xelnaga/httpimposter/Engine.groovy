package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.model.Report
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset

class Engine {

    private List<Interaction> expectations
    private List<Interaction> interactions

    ResponseProvider responseProvider

    Engine() {

        expectations = []
        interactions = []

        responseProvider = new MappedResponseProvider()
    }

    Report getReport() {
        return new Report(expectations, interactions)
    }

    void expect(int cardinality, Interaction expectation) {

        cardinality.times {
            expectations << expectation
            responseProvider.add(expectation)
        }
    }

    Interaction interact(RequestPattern request) {

        ResponsePreset response = responseProvider.get(request)

        Interaction interaction = new Interaction(request, response)
        interactions << interaction

        return interaction
    }

    void reset() {

        expectations.clear()
        interactions.clear()

        responseProvider.reset()
    }
}

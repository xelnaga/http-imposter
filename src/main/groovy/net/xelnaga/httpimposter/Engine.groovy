package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.factory.ResponsePresetFactory
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset
import net.xelnaga.httpimposter.transport.Report

class Engine {

    private static final ResponsePreset UNEXPECTED = new ResponsePresetFactory().makeUnexpected()

    private List<RequestPattern> expectations = []
    private List<RequestPattern> interactions = []

    private Map<RequestPattern, ResponsePreset> responses = [:]

    Report getReport() {

        return new Report(
                expectations: expectations,
                interactions: interactions
        )
    }

    void expect(int cardinality, RequestPattern request, ResponsePreset response) {

        cardinality.times {
            expectations << request
        }

        responses.put(request, response)
    }

    ResponsePreset interact(RequestPattern request) {

        interactions << request

        return responses.get(request, UNEXPECTED)
    }

    void reset() {

        expectations.clear()
        interactions.clear()

        responses.clear()
    }
}

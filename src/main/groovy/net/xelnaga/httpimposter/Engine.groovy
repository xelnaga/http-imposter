package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.factory.ResponsePresetFactory
import net.xelnaga.httpimposter.model.ResponsePreset
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.transport.Report

class Engine {

    private static final ResponsePreset UNEXPECTED = new ResponsePresetFactory().makeUnexpected()

    private Map<RequestPattern, Interaction> responses = [:]

    private List<RequestPattern> expectations = []
    private List<RequestPattern> interactions = []

    Report getReport() {

        return new Report(
                expectations: expectations,
                interactions: interactions,
                legacy: responses.values() as List,
        )
    }

    void expect(int cardinality, RequestPattern pattern, ResponsePreset preset) {

        cardinality.times {
            expectations << pattern
        }

        Interaction interaction = new Interaction(
                requestPattern: pattern,
                responsePreset: preset,
                expected: cardinality,
                actual: 0)

        responses.put(pattern, interaction)
    }

    ResponsePreset interact(RequestPattern pattern) {

        interactions << pattern

        Interaction interaction = responses.get(pattern)
        if (!interaction) {
            interaction = makeUnexpectedInteraction(pattern)
            responses.put(pattern, interaction)
        }

        interaction.actual++

        return interaction.responsePreset
    }

    void reset() {

        expectations.clear()
        interactions.clear()

        responses.clear()
    }

    private Interaction makeUnexpectedInteraction(RequestPattern requestPattern) {

        return new Interaction(
                requestPattern: requestPattern,
                responsePreset: UNEXPECTED,
                expected: 0,
                actual: 0)
    }
}

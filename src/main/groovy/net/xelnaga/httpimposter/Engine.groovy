package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.factory.ResponsePresetFactory
import net.xelnaga.httpimposter.model.ResponsePreset
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.Interaction

class Engine {

    private static final ResponsePreset UNEXPECTED = new ResponsePresetFactory().makeUnexpected()

    private Map<RequestPattern, Interaction> expectations = [:]
    private List<RequestPattern> interactions = []

    List<Interaction> getExpectations() {
        return expectations.values() as List
    }

    List<RequestPattern> getInteractions() {
        return interactions
    }

    void expect(int cardinality, RequestPattern pattern, ResponsePreset preset) {

        Interaction interaction = new Interaction(
                requestPattern: pattern,
                responsePreset: preset,
                expected: cardinality,
                actual: 0)

        expectations.put(pattern, interaction)
    }

    ResponsePreset interact(RequestPattern pattern) {

        interactions << pattern

        Interaction interaction = expectations.get(pattern)
        if (!interaction) {
            interaction = makeUnexpectedInteraction(pattern)
            expectations.put(pattern, interaction)
        }

        interaction.actual++

        return interaction.responsePreset
    }

    void reset() {

        expectations.clear()
        interactions.clear()
    }

    private Interaction makeUnexpectedInteraction(RequestPattern requestPattern) {

        return new Interaction(
                requestPattern: requestPattern,
                responsePreset: UNEXPECTED,
                expected: 0,
                actual: 0)
    }
}

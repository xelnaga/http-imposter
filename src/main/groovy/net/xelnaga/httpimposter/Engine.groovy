package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.factory.ResponsePresetFactory
import net.xelnaga.httpimposter.model.ResponsePreset
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.Interaction

class Engine {

    private static final ResponsePreset UNEXPECTED = new ResponsePresetFactory().makeUnexpected()

    private Map<RequestPattern, Interaction> interactions = [:]

    void expect(RequestPattern requestPattern, ResponsePreset responsePreset) {

        Interaction interaction = new Interaction(
                requestPattern: requestPattern,
                responsePreset: responsePreset,
                expected: 1,
                actual: 0)

        interactions.put(requestPattern, interaction)
    }

    ResponsePreset interact(RequestPattern requestPattern) {

        Interaction interaction = interactions.get(requestPattern)
        if (!interaction) {
            interaction = makeUnmatchedInteraction(requestPattern)
            interactions.put(requestPattern, interaction)
        }

        interaction.actual++

        return interaction.responsePreset
    }

    boolean verify() {
        return true
    }

    void reset() {
        interactions.clear()
    }

    private Interaction makeUnmatchedInteraction(RequestPattern requestPattern) {

        return new Interaction(
                requestPattern: requestPattern,
                responsePreset: UNEXPECTED,
                expected: 0,
                actual: 0)
    }
}

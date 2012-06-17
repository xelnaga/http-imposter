package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.factory.ResponsePresetFactory
import net.xelnaga.httpimposter.model.ResponsePreset
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.Interaction

class Engine {

    private static final ResponsePreset UNEXPECTED = new ResponsePresetFactory().makeUnexpected()

    private Map<RequestPattern, Interaction> interactions = [:]

    void expect(int cardinality, RequestPattern requestPattern, ResponsePreset responsePreset) {

        Interaction interaction = new Interaction(
                requestPattern: requestPattern,
                responsePreset: responsePreset,
                expected: cardinality,
                actual: 0)

        interactions.put(requestPattern, interaction)
    }

    ResponsePreset interact(RequestPattern requestPattern) {

        println requestPattern.hashCode()

        Interaction interaction = interactions.get(requestPattern)
        println interaction
        if (!interaction) {
            interaction = makeUnmatchedInteraction(requestPattern)
            interactions.put(requestPattern, interaction)
        }

        interaction.actual++

        return interaction.responsePreset
    }

    List<Interaction> verify() {
        println interactions.size()
        return interactions.values() as List
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

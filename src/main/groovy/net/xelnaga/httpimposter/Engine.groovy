package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.factory.ResponsePresetFactory
import net.xelnaga.httpimposter.model.ResponsePreset
import net.xelnaga.httpimposter.model.RequestPattern

class Engine {

    private static final ResponsePreset UNMATCHED = new ResponsePresetFactory().makeUnmatched()

    private Map<RequestPattern, ResponsePreset> expectations = [:]

    void expect(RequestPattern requestPattern, ResponsePreset responsePreset) {
        expectations.put(requestPattern, responsePreset)
    }

    ResponsePreset interact(RequestPattern requestPattern) {
        return expectations.get(requestPattern, UNMATCHED)
    }

    boolean verify() {
        return true
    }

    void reset() {
        expectations.clear()
    }
}

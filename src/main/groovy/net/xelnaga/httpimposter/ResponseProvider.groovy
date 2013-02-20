package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset

interface ResponseProvider {

    ResponsePreset get(RequestPattern request)
    void add(Interaction interaction)

    void reset()
}

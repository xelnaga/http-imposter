package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset

class MappedResponseProvider implements ResponseProvider {

    private Map<RequestPattern, ResponsePreset> map

    MappedResponseProvider() {
        map = [:]
    }

    @Override
    ResponsePreset get(RequestPattern request) {
        return map.get(request, )
    }

    @Override
    void add(Interaction interaction) {
        map.put(interaction.request, interaction.response)
    }

    @Override
    void reset() {
        map.clear()
    }
}

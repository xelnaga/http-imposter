package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.factory.ResponsePresetFactory
import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset

class MappedResponseProvider implements ResponseProvider {

    private Map<RequestPattern, ResponsePreset> map

    ResponsePresetFactory responsePresetFactory

    MappedResponseProvider() {

        map = [:]
        responsePresetFactory = new ResponsePresetFactory()
    }

    @Override
    ResponsePreset get(RequestPattern request) {
        ResponsePreset response = map.findResult { RequestPattern expectedRequest, ResponsePreset response ->
            if (expectedRequest.matches(request)) {
                return response
            }
        }
        if (response) {
            return response
        }

        return responsePresetFactory.makeUnexpected()
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

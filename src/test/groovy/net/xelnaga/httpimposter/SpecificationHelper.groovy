package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset

class SpecificationHelper {

    Interaction makeInteraction(int index) {

        return new Interaction(
                makeRequestPattern(index),
                makeResponsePreset(index)
        )
    }

    RequestPattern makeRequestPattern(int index) {

        return new RequestPattern(
                uri: '/someuri' + index,
                method: 'somemethod' + index,
                headers: [
                        new HttpHeader('headername' + index, 'headervalue' + index)
                ],
                body: 'somebody' + index
        )
    }

    ResponsePreset makeResponsePreset(int index) {

        return new ResponsePreset(
                status: 200 + index,
                headers: [
                        new HttpHeader('headername' + index, 'headervalue' + index)
                ],
                body: 'somebody' + index
        )
    }
}

package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.DefaultHttpHeader
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

    Interaction makeInteraction(int index, HttpHeader httpHeader) {

        return new Interaction(
            makeRequestPattern(index, httpHeader),
            makeResponsePreset(index, httpHeader)
        )
    }

    RequestPattern makeRequestPattern(int index) {

        return makeRequestPattern(index, new DefaultHttpHeader('headername' + index, 'headervalue' + index))
    }

    RequestPattern makeRequestPattern(int index, HttpHeader httpHeader) {

        return new RequestPattern(
            uri: '/someuri' + index,
            method: 'somemethod' + index,
            headers: [
                httpHeader
            ],
            body: 'somebody' + index
        )
    }

    ResponsePreset makeResponsePreset(int index) {
        return makeResponsePreset(index, new DefaultHttpHeader('headername' + index, 'headervalue' + index))
    }

    ResponsePreset makeResponsePreset(int index, HttpHeader httpHeader) {

        return new ResponsePreset(
            status: 200 + index,
            headers: [
                httpHeader
            ],
            body: 'somebody' + index
        )
    }
}

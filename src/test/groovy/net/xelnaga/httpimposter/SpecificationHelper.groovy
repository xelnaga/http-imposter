package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.DefaultHttpHeader
import net.xelnaga.httpimposter.model.DefaultBody
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.model.Body
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

    Interaction makeInteraction(int index, Body requestBody) {

        HttpHeader httpHeader = new DefaultHttpHeader('headername' + index, 'headervalue' + index)

        return new Interaction(
            makeRequestPattern(index, requestBody),
            makeResponsePreset(index, httpHeader)
        )
    }

    Interaction makeInteraction(int index, HttpHeader httpHeader, Body requestBody) {

        return new Interaction(
            makeRequestPattern(index, httpHeader, requestBody),
            makeResponsePreset(index, httpHeader)
        )
    }

    RequestPattern makeRequestPattern(int index) {

        return makeRequestPattern(index, new DefaultHttpHeader('headername' + index, 'headervalue' + index))
    }

    RequestPattern makeRequestPattern(int index, HttpHeader httpHeader) {

        Body requestBody = new DefaultBody('requestBody' + index)

        return makeRequestPattern(index, httpHeader, requestBody)
    }

    RequestPattern makeRequestPattern(int index, Body requestBody) {

        HttpHeader httpHeader = new DefaultHttpHeader('headername' + index, 'headervalue' + index)

        return makeRequestPattern(index, httpHeader, requestBody)
    }

    RequestPattern makeRequestPattern(int index, HttpHeader httpHeader, Body requestBody) {

        return new RequestPattern(
            uri: '/someuri' + index,
            method: 'somemethod' + index,
            headers: [
                httpHeader
            ],
            body: requestBody
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
            body: new DefaultBody('responseBody' + index)
        )
    }
}

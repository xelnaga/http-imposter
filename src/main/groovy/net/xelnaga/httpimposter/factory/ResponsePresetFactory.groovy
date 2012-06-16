package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.ResponsePreset

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import net.xelnaga.httpimposter.model.HttpHeader

class ResponsePresetFactory {

    ResponsePreset makeUnmatched() {

        return new ResponsePreset(
                status: SC_INTERNAL_SERVER_ERROR,
                headers: [
                        new HttpHeader('Content-Type', 'text/plain')
                ],
                body: 'No match found for http request'
        )
    }
}

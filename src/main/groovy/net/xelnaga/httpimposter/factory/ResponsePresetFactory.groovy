package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.ResponsePreset

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import net.xelnaga.httpimposter.model.DefaultHttpHeader

class ResponsePresetFactory {

    ResponsePreset makeUnexpected() {

        return new ResponsePreset(
                status: SC_INTERNAL_SERVER_ERROR,
                headers: [
                        new DefaultHttpHeader('Content-Type', 'text/plain')
                ],
                body: 'UNEXPECTED REQUEST PATTERN'
        )
    }
}

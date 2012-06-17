package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.ResponsePreset
import spock.lang.Specification

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR

class ResponsePresetFactorySpec extends Specification {

    ResponsePresetFactory factory

    void setup() {
        factory = new ResponsePresetFactory()
    }

    def 'make unexpected'() {

        when:
            ResponsePreset responsePreset = factory.makeUnexpected()

        then:
            responsePreset == new ResponsePreset(
                    status: SC_INTERNAL_SERVER_ERROR,
                    headers: [
                            new HttpHeader('Content-Type', 'text/plain')
                    ],
                    body: """
    _   _ _   _ _______  ______  _____ ____ _____ _____ ____    ____  _____ ___  _   _ _____ ____ _____
   | | | | \\ | | ____\\ \\/ /  _ \\| ____/ ___|_   _| ____|  _ \\  |  _ \\| ____/ _ \\| | | | ____/ ___|_   _|
   | | | |  \\| |  _|  \\  /| |_) |  _|| |     | | |  _| | | | | | |_) |  _|| | | | | | |  _| \\___ \\ | |
   | |_| | |\\  | |___ /  \\|  __/| |__| |___  | | | |___| |_| | |  _ <| |__| |_| | |_| | |___ ___) || |
    \\___/|_| \\_|_____/_/\\_\\_|   |_____\\____| |_| |_____|____/  |_| \\_\\_____\\__\\_\\\\___/|_____|____/ |_|

   An unexpected request pattern has been received by http imposter

"""
            )
    }
}

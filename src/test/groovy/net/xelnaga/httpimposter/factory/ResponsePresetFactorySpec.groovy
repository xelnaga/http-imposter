package net.xelnaga.httpimposter.factory

import spock.lang.Specification
import net.xelnaga.httpimposter.model.ResponsePreset

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import net.xelnaga.httpimposter.model.HttpHeader

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

 _   _ _   _ _______  ______  _____ ____ _____ _____ ____
| | | | \\ | | ____\\ \\/ /  _ \\| ____/ ___|_   _| ____|  _ \\
| | | |  \\| |  _|  \\  /| |_) |  _|| |     | | |  _| | | | |
| |_| | |\\  | |___ /  \\|  __/| |__| |___  | | | |___| |_| |
 \\___/|_| \\_|_____/_/\\_\\_|   |_____\\____| |_| |_____|____/

 _   _ _____ _____ ____       ____  _____ ___  _   _ _____ ____ _____
| | | |_   _|_   _|  _ \\     |  _ \\| ____/ _ \\| | | | ____/ ___|_   _|
| |_| | | |   | | | |_) |    | |_) |  _|| | | | | | |  _| \\___ \\ | |
|  _  | | |   | | |  __/     |  _ <| |__| |_| | |_| | |___ ___) || |
|_| |_| |_|   |_| |_|        |_| \\_\\_____\\__\\_\\\\___/|_____|____/ |_|

"""
            )
    }
}

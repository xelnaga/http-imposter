package net.xelnaga.httpimposter

import com.google.gson.Gson
import net.xelnaga.httpimposter.filter.PassThroughFilter
import net.xelnaga.httpimposter.model.Interaction
import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest


class HttpImposterIntegrationSpec extends Specification {

    private static final String BASE64_ENCODED_BODY = 'ZGVjb2RlZA=='
    private static final String DECODED_BODY = 'decoded'

    HttpImposter httpImposter
    Gson gson

    Engine mockEngine

    void setup() {
        httpImposter = new HttpImposter()
        httpImposter.filter = new PassThroughFilter()

        mockEngine = Mock(Engine)
        httpImposter.engine = mockEngine


        gson = new Gson()
    }

    def 'deserialize real-world json'() {

        given:
            Map contentMap = [
                request: [
                    uri: 'uri',
                    method: 'method',
                    headers: [],
                    body: [
                        type: 'default',
                        value: BASE64_ENCODED_BODY
                    ]
                ],
                response: [
                    status: 200,
                    headers: [],
                    body: [
                        type: 'default',
                        value: BASE64_ENCODED_BODY
                    ]
                ]
            ]
            byte[] content = gson.toJson(contentMap).bytes

            HttpServletRequest mockRequest = new MockHttpServletRequest()
            mockRequest.setContent(content)

        when:
            httpImposter.expect(mockRequest)

        then:
            1 * mockEngine.expect( (Interaction) {Interaction interaction ->
                if (interaction.request.body.value != DECODED_BODY ) {
                    return false
                }
                return true
            })
            0 * _
    }
}

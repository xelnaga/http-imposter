package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.DefaultBody
import net.xelnaga.httpimposter.model.Body
import net.xelnaga.httpimposter.model.BodyTypes
import spock.lang.Specification

class DefaultBodyFactorySpec extends Specification {

    private static final String TEST_VALUE = "test value"

    DefaultBodyFactory factory = new DefaultBodyFactory()

    void "make default body"() {
        given:
            Map<String, String> bodyMap = [value: TEST_VALUE]

        when:
            Body body = factory.makeBody(bodyMap)

        then:
            body instanceof DefaultBody
            body.value == TEST_VALUE
            body.type == BodyTypes.DEFAULT
    }
}

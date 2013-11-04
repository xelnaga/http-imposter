package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.Body
import net.xelnaga.httpimposter.model.BodyTypes
import spock.lang.Specification

class RegexMatchingBodyFactorySpec extends Specification {

    private static final String TEST_VALUE_NAME = "value"
    private static final String TEST_REGEX = '.*'

    RegexMatchingBodyFactory factory

    void setup() {
        factory = new RegexMatchingBodyFactory()
    }

    void "make regular expression body"() {
        given:
            Map<String, String> mockBodyMap = Mock(Map)

        when:
            Body body = factory.makeBody(mockBodyMap)

        then:
            1 * mockBodyMap.get(TEST_VALUE_NAME) >> TEST_REGEX
            0 * _

        and:
            body.type == BodyTypes.REGEX
            body.value == TEST_REGEX
    }
}

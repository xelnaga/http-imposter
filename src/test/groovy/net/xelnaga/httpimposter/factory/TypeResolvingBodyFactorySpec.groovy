package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.RegexMatchingBody
import net.xelnaga.httpimposter.model.Body
import net.xelnaga.httpimposter.model.BodyTypes
import spock.lang.Specification
import spock.lang.Unroll

class TypeResolvingBodyFactorySpec extends Specification {

    private static final String TEST_VALUE = "test value"
    private static final String TEST_TYPE_NAME = "type"
    private static final String TEST_VALUE_NAME = "value"

    @Unroll
    def "create default body"() {

        given:
            TypeResolvingBodyFactory bodyFactory = new TypeResolvingBodyFactory()
            Map mockBodyMap = Mock(Map)

        when:
            Body body = bodyFactory.makeBody(mockBodyMap)

        then:
            1 * mockBodyMap.get(TEST_TYPE_NAME) >> bodyType
            1 * mockBodyMap.get(TEST_VALUE_NAME) >> TEST_VALUE
            0 * _

        and:
            body.type == BodyTypes.DEFAULT
            body.value == TEST_VALUE

        where:
            bodyType << [
                null,
                "INVALID_TYPE",
                BodyTypes.DEFAULT
            ]
    }

    def "create regex body"() {

        given:
            TypeResolvingBodyFactory bodyFactory = new TypeResolvingBodyFactory()
            Map mockBodyFactories = Mock(Map)
            bodyFactory.bodyFactoriesMap = mockBodyFactories

        and:
            Map mockBodyMap = Mock(Map)
            RegexMatchingBodyFactory mockRegexBodyFactory = Mock(RegexMatchingBodyFactory)
            RegexMatchingBody mockRegexBody = Mock(RegexMatchingBody)

        when:
            Body body = bodyFactory.makeBody(mockBodyMap)

        then:
            1 * mockBodyMap.get(TEST_TYPE_NAME) >> BodyTypes.REGEX
            1 * mockBodyFactories.get(BodyTypes.REGEX) >> mockRegexBodyFactory
            1 * mockRegexBodyFactory.makeBody(mockBodyMap) >> mockRegexBody
            0 * _

        and:
            body.is(mockRegexBody)
    }
}

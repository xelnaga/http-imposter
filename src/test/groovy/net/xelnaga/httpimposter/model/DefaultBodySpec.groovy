package net.xelnaga.httpimposter.model

import spock.lang.Specification
import spock.lang.Unroll

class DefaultBodySpec extends Specification {

    private static final String TEST_BODY_TEXT_1 = 'body text 1'
    private static final String TEST_BODY_TEXT_2 = 'body text 2'

    void "equals"() {

        given:
            Body defaultBody = new DefaultBody(value1)
            Body defaultBody2 = new DefaultBody(value2)

        expect:
            defaultBody.equals(defaultBody2) == result

        where:
            value1           | value2           | result
            TEST_BODY_TEXT_1 | TEST_BODY_TEXT_1 | true
            TEST_BODY_TEXT_1 | TEST_BODY_TEXT_2 | false
    }

    void "hash code"() {

        given:
            Body defaultBody = new DefaultBody(value1)
            Body defaultBody2 = new DefaultBody(value2)

        expect:
            (defaultBody.hashCode() == defaultBody2.hashCode()) == result

        where:
            value1           | value2           | result
            TEST_BODY_TEXT_1 | TEST_BODY_TEXT_1 | true
            TEST_BODY_TEXT_1 | TEST_BODY_TEXT_2 | false
    }

    void 'matches'() {

        given:
            DefaultBody body = new DefaultBody(TEST_BODY_TEXT_1)

        when:
            boolean isMatched = body.matches(otherBody)

        then:
            isMatched == expectedMatch

        where:
            expectedMatch | otherBody
            true          | new DefaultBody(TEST_BODY_TEXT_1)
            false         | new DefaultBody(TEST_BODY_TEXT_2)
    }

    @Unroll
    void "the body is #scenario"() {

        given:
            DefaultBody body = new DefaultBody(text)

        when:
            boolean empty = body.isEmpty()

        then:
            empty == expectedIsEmpty

        where:
            scenario       | text         | expectedIsEmpty
            "empty"        | ""           | true
            "not empty"    | "any string" | false
            "empty (null)" | null         | true
    }

    void "get default body type"() {

        given:
            DefaultBody body = new DefaultBody('')

        when:
            String bodyType = body.getType()

        then:
            bodyType == BodyTypes.DEFAULT
    }

    void "toString includes type, value and delimiters"() {

        given:
            DefaultBody body = new DefaultBody('a body')

        when:
            String result = body

        then:
            result == "[type: 'default', body: 'a body']"
    }
}

package net.xelnaga.httpimposter.model

import spock.lang.Specification

class RegexBodySpec extends Specification {

    void "match regular expression"() {

        given:
            Body regexBody = new RegexMatchingBody(regex)
            Body defaultBody = new DefaultBody(text)

        when:
            boolean matches = regexBody.matches(defaultBody)

        then:
            0 * _

        and:
            matches == matched

        where:
            regex       | text                | matched
            /[a-zA-Z]+/ | "matched"           | true
            /[a-zA-Z]+/ | "un matched string" | false
    }
}

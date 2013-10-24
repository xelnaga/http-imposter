package net.xelnaga.httpimposter.model

import spock.lang.Specification
import spock.lang.Unroll

class RequestPatternSpec extends Specification {

    def 'headers are initialised sorted set'() {
        expect:
            new RequestPattern().headers instanceof SortedSet
    }

    @Unroll
    def 'matches using regular expression'() {
        given:
            RequestPattern pattern1 = new RequestPattern(headers: [new RegexMatchingHttpHeader('name', expression)])
            RequestPattern pattern2 = new RequestPattern(headers: [new DefaultHttpHeader('name', value)])

        expect:
            result == pattern1.matches(pattern2)

        where:
            expression               | value                    | result
            'expressionmatchesvalue' | 'expressionmatchesvalue' | true
            '.*'                     | 'somevalue'              | true
            '^banana$'               | 'apple'                  | false
            ''                       | ''                       | true
    }

    @Unroll
    def 'matches (same value)'() {
        given:
            RequestPattern pattern1 = new RequestPattern(headers: [new DefaultHttpHeader(name1, 'value')])
            RequestPattern pattern2 = new RequestPattern(headers: [new DefaultHttpHeader(name2, 'value')])

        expect:
            result == pattern1.matches(pattern2)

        where:
            name1  | name2  | result
            'A'    | 'a'    | true
            'a'    | 'A'    | true
            'a'    | 'a'    | true
            'a'    | 'b'    | false
    }

    @Unroll
    def 'matches (different value)'() {
        given:
            RequestPattern pattern1 = new RequestPattern(headers: [new DefaultHttpHeader('name', value1)])
            RequestPattern pattern2 = new RequestPattern(headers: [new DefaultHttpHeader('name', value2)])

        expect:
            result == pattern1.matches(pattern2)

        where:
            value1 | value2 | result
            'a'    | 'a'    | true
            'a'    | 'A'    | false
            'A'    | 'a'    | false
            'a'    | 'b'    | false
    }

}


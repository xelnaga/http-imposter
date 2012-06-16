package net.xelnaga.httpimposter.model

import spock.lang.Specification

class RequestPatternSpec extends Specification {

    def 'headers are initialised sorted set'() {
        
        expect:
            new RequestPattern().headers instanceof SortedSet
    }
}

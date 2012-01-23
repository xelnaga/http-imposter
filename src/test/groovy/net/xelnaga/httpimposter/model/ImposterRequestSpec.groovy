package net.xelnaga.httpimposter.model

import spock.lang.Specification

class ImposterRequestSpec extends Specification {

    def 'headers are initialised sorted set'() {
        
        expect:
            new ImposterRequest().headers instanceof SortedSet
    }
}

package net.xelnaga.httpimposter.filter

import net.xelnaga.httpimposter.model.HttpHeader
import spock.lang.Specification

class HeaderNameExclusionFilterSpec extends Specification {

    def 'is matchable with no exclusions'() {

        given:
            HeaderNameExclusionFilter filter = new HeaderNameExclusionFilter([])

        expect:
            filter.isMatchable(new HttpHeader('gibberish', 'rubbish'))
    }
    
    def 'is matchable with exclusions'() {
        
        given:
            HeaderNameExclusionFilter filter = new HeaderNameExclusionFilter([ 'Host', 'uSeR-aGeNt' ])

        expect:
            filter.isMatchable(new HttpHeader('gibberish', 'rubbish'))
            !filter.isMatchable(new HttpHeader('Host', 'somehost'))
            !filter.isMatchable(new HttpHeader('User-Agent', 'Ghetto-Browser 9000'))
    }
}

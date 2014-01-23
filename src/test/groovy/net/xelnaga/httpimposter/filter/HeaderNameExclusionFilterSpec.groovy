package net.xelnaga.httpimposter.filter

import net.xelnaga.httpimposter.model.DefaultHttpHeader
import spock.lang.Specification

class HeaderNameExclusionFilterSpec extends Specification {

    def 'is matchable with no exclusions'() {

        given:
            HeaderNameExclusionFilter filter = new HeaderNameExclusionFilter([])

        expect:
            filter.isMatchable(new DefaultHttpHeader('gibberish', 'rubbish'))
    }
    
    def 'is matchable with exclusions'() {
        
        given:
            HeaderNameExclusionFilter filter = new HeaderNameExclusionFilter([ 'Host', 'uSeR-aGeNt' ])

        expect:
            filter.isMatchable(new DefaultHttpHeader('gibberish', 'rubbish'))
            !filter.isMatchable(new DefaultHttpHeader('Host', 'somehost'))
            !filter.isMatchable(new DefaultHttpHeader('User-Agent', 'Ghetto-Browser 9000'))
    }
}

package net.xelnaga.httpimposter.filter

import spock.lang.Specification
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.filter.HeaderNameExclusionFilter

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

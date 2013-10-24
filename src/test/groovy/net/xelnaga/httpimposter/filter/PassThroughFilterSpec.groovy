package net.xelnaga.httpimposter.filter

import net.xelnaga.httpimposter.model.DefaultHttpHeader
import spock.lang.Specification

class PassThroughFilterSpec extends Specification {

    def 'is matchable'() {
        
        given:
            HttpHeaderFilter filter = new PassThroughFilter()
        
        expect:
            filter.isMatchable(null)
            filter.isMatchable(new DefaultHttpHeader('qwerty', 'blah'))
    }
}

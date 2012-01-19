package net.xelnaga.httpimposter

import spock.lang.Specification
import net.xelnaga.httpimposter.model.ImposterRequest
import net.xelnaga.httpimposter.model.ImposterResponse

class ImposterMappingSpec extends Specification {

    private ImposterMapping mapping
    
    void setup() {
        mapping = new ImposterMapping()
    }

    def 'get when mapping exists'() {

        given:
            ImposterRequest request = new ImposterRequest(body: 'hello')
            ImposterResponse response = new ImposterResponse(body: 'world')

        when:
            mapping.put(request, response)

        then:
            mapping.get(request) == response
    }

    def 'get when mapping does not exist'() {
    
        given:
            ImposterRequest request = new ImposterRequest(body: 'hello')
        
        expect:
            mapping.get(request) == null
    }

    def 'clear'() {

        given:
            ImposterRequest request = new ImposterRequest(body: 'hello')
            ImposterResponse response = new ImposterResponse(body: 'world')
            mapping.put(request, response)

        when:
            mapping.clear()

        then:
            mapping.get(request) == null
    }
}

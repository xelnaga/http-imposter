package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.ImposterRequest
import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification

class ImposterRequestFactorySpec extends Specification {
    
    private ImposterRequestFactory factory
    private HttpHeaderFilter mockHttpHeaderFilter
    
    void setup() {
        factory = new ImposterRequestFactory()
        
        mockHttpHeaderFilter = Mock(HttpHeaderFilter)
        factory.filter = mockHttpHeaderFilter
    }
    
    def 'from http request'() {
        
        given:
            MockHttpServletRequest httpRequest = new MockHttpServletRequest(
                    requestURI: '/fruity/pineapple',
                    method: 'mango',
                    contentType: 'text/banana',
                    content: 'qwerty'.bytes
            )
            httpRequest.addHeader('Pineapple', 'Passionfruit')
            httpRequest.addHeader('Durian', 'Stinky')

        when:
            ImposterRequest imposterRequest = factory.fromHttpRequest(httpRequest)

        then:
            (1) * mockHttpHeaderFilter.isMatchable(new HttpHeader('Content-Type', 'text/banana')) >> true
            (1) * mockHttpHeaderFilter.isMatchable(new HttpHeader('Pineapple', 'Passionfruit')) >> true
            (1) * mockHttpHeaderFilter.isMatchable(new HttpHeader('Durian', 'Stinky')) >> false
            (0) * _._

        and:
            imposterRequest == new ImposterRequest(
                    uri: '/fruity/pineapple',
                    method: 'mango',
                    headers: [
                            new HttpHeader('Content-Type', 'text/banana'),
                            new HttpHeader('Pineapple', 'Passionfruit')
                    ],
                    body: 'qwerty'
            )
    }

    def 'from json'() {

        given:
            Map jsonMap = [
                    headers: [
                            [name: 'Content-Type', value: 'text/banana'],
                            [name: 'Pineapple', value: 'Passionfruit'],
                            [name: 'Durian', value: 'Stinky']
                    ],
                    uri: '/fruity/pineapple',
                    body: 'Ym9keXRlc3Q=',
                    method: 'mango'
            ]

        when:
            ImposterRequest imposterRequest = factory.fromJson(jsonMap)

        then:
            (1) * mockHttpHeaderFilter.isMatchable(new HttpHeader('Content-Type', 'text/banana')) >> true
            (1) * mockHttpHeaderFilter.isMatchable(new HttpHeader('Pineapple', 'Passionfruit')) >> true
            (1) * mockHttpHeaderFilter.isMatchable(new HttpHeader('Durian', 'Stinky')) >> false
            (0) * _._

        and:
            imposterRequest == new ImposterRequest(
                    uri: '/fruity/pineapple',
                    method: 'mango',
                    headers: [
                            new HttpHeader('Content-Type', 'text/banana'),
                            new HttpHeader('Pineapple', 'Passionfruit')
                    ],
                    body: 'bodytest')
    }
}

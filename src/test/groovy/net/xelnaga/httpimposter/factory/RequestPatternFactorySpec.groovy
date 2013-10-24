package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.DefaultHttpHeader
import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification

class RequestPatternFactorySpec extends Specification {
    
    RequestPatternFactory factory
    HttpHeaderFactory mockHeaderFactory

    HttpHeaderFilter mockHttpHeaderFilter
    
    void setup() {

        factory = new RequestPatternFactory()

        mockHeaderFactory = Mock(HttpHeaderFactory)
        factory.headerFactory = mockHeaderFactory
        
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

        and:
            HttpHeader httpHeader1 = new DefaultHttpHeader('Content-Type', 'text/banana')
            HttpHeader httpHeader2 = new DefaultHttpHeader('Pineapple', 'Passionfruit')
            HttpHeader httpHeader3 = new DefaultHttpHeader('Durian', 'Stinky')

        when:
            RequestPattern requestPattern = factory.fromHttpRequest(httpRequest)

        then:
            1 * mockHeaderFactory.makeHeader([name: 'Content-Type', value: 'text/banana']) >> httpHeader1
            1 * mockHeaderFactory.makeHeader([name: 'Pineapple', value: 'Passionfruit']) >> httpHeader2
            1 * mockHeaderFactory.makeHeader([name: 'Durian', value: 'Stinky']) >> httpHeader3
            1 * mockHttpHeaderFilter.isMatchable(httpHeader1) >> true
            1 * mockHttpHeaderFilter.isMatchable(httpHeader2) >> true
            1 * mockHttpHeaderFilter.isMatchable(httpHeader3) >> false
            0 * _._

        and:
            requestPattern == new RequestPattern(
                    uri: '/fruity/pineapple',
                    method: 'mango',
                    headers: [
                            new DefaultHttpHeader('Content-Type', 'text/banana'),
                            new DefaultHttpHeader('Pineapple', 'Passionfruit')
                    ],
                    body: 'qwerty'
            )
    }

    def 'from http request with query string'() {

        given:
            MockHttpServletRequest httpRequest = new MockHttpServletRequest(
                    requestURI: '/fruity/pineapple',
                    queryString: 'peaches=true&oranges=false',
                    method: 'mango',
                    contentType: 'text/banana',
                    content: 'qwerty'.bytes
            )
            httpRequest.addHeader('Pineapple', 'Passionfruit')
            httpRequest.addHeader('Durian', 'Stinky')

        and:
            HttpHeader httpHeader1 = new DefaultHttpHeader('Content-Type', 'text/banana')
            HttpHeader httpHeader2 = new DefaultHttpHeader('Pineapple', 'Passionfruit')
            HttpHeader httpHeader3 = new DefaultHttpHeader('Durian', 'Stinky')

        when:
            RequestPattern requestPattern = factory.fromHttpRequest(httpRequest)

        then:
            1 * mockHeaderFactory.makeHeader([name: 'Content-Type', value: 'text/banana']) >> httpHeader1
            1 * mockHeaderFactory.makeHeader([name: 'Pineapple', value: 'Passionfruit']) >> httpHeader2
            1 * mockHeaderFactory.makeHeader([name: 'Durian', value: 'Stinky']) >> httpHeader3
            1 * mockHttpHeaderFilter.isMatchable(httpHeader1) >> true
            1 * mockHttpHeaderFilter.isMatchable(httpHeader2) >> true
            1 * mockHttpHeaderFilter.isMatchable(httpHeader3) >> false
            0 * _._

        and:
            requestPattern == new RequestPattern(
                    uri: '/fruity/pineapple?peaches=true&oranges=false',
                    method: 'mango',
                    headers: [
                            new DefaultHttpHeader('Content-Type', 'text/banana'),
                            new DefaultHttpHeader('Pineapple', 'Passionfruit')
                    ],
                    body: 'qwerty'
            )
    }
}

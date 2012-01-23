package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.ImposterRequest
import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification
import org.gmock.WithGMock
import net.xelnaga.httpimposter.filter.HttpHeaderFilter

@WithGMock
class ImposterRequestFactorySpec extends Specification {
    
    private ImposterRequestFactory factory
    private HttpHeaderFilter mockFilter
    
    void setup() {
        factory = new ImposterRequestFactory()
        
        mockFilter = mock(HttpHeaderFilter)
        factory.filter = mockFilter
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
            mockFilter.isMatchable(new HttpHeader('Content-Type', 'text/banana')).returns(true)
            mockFilter.isMatchable(new HttpHeader('Pineapple', 'Passionfruit')).returns(true)
            mockFilter.isMatchable(new HttpHeader('Durian', 'Stinky')).returns(false)

        when:
            ImposterRequest imposterRequest = null
            play {
                imposterRequest = factory.fromHttpRequest(httpRequest)
            }

        then:
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
            String json = '''{
    "headers": [
        {
            "name": "Content-Type",
            "value": "text/banana"
        },
        {
            "name": "Pineapple",
            "value": "Passionfruit"
        },
        {
            "name": "Durian",
            "value": "Stinky"
        }
    ],
    "uri": "/fruity/pineapple",
    "body": "qwerty",
    "method": "mango"
}'''
        and:
            mockFilter.isMatchable(new HttpHeader('Content-Type', 'text/banana')).returns(true)
            mockFilter.isMatchable(new HttpHeader('Pineapple', 'Passionfruit')).returns(true)
            mockFilter.isMatchable(new HttpHeader('Durian', 'Stinky')).returns(false)

        when:
            ImposterRequest imposterRequest = null
            play {
                imposterRequest = factory.fromJson(json)
            }

        then:
            imposterRequest == new ImposterRequest(
                    uri: '/fruity/pineapple',
                    method: 'mango',
                    headers: [
                            new HttpHeader('Content-Type', 'text/banana'),
                            new HttpHeader('Pineapple', 'Passionfruit')
                    ],
                    body: 'qwerty')
    }
}

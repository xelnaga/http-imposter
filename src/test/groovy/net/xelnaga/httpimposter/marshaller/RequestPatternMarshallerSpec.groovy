package net.xelnaga.httpimposter.marshaller

import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.RequestPattern
import spock.lang.Specification

class RequestPatternMarshallerSpec extends Specification {

    RequestPatternMarshaller marshaller

    HttpHeaderFilter mockHttpHeaderFilter

    void setup() {

        marshaller = new RequestPatternMarshaller()

        mockHttpHeaderFilter = Mock(HttpHeaderFilter)
        marshaller.filter = mockHttpHeaderFilter
    }

    def 'from json'() {

        given:
            Map json = [
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
            RequestPattern requestPattern = marshaller.fromJson(json)

        then:
            1 * mockHttpHeaderFilter.isMatchable(new HttpHeader('Content-Type', 'text/banana')) >> true
            1 * mockHttpHeaderFilter.isMatchable(new HttpHeader('Pineapple', 'Passionfruit')) >> true
            1 * mockHttpHeaderFilter.isMatchable(new HttpHeader('Durian', 'Stinky')) >> false
            0 * _._

        and:
            requestPattern == new RequestPattern(
                    uri: '/fruity/pineapple',
                    method: 'mango',
                    headers: [
                            new HttpHeader('Content-Type', 'text/banana'),
                            new HttpHeader('Pineapple', 'Passionfruit')
                    ],
                    body: 'bodytest')
    }
}

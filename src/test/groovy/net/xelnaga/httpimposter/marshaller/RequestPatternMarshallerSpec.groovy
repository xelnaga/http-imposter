package net.xelnaga.httpimposter.marshaller

import net.xelnaga.httpimposter.factory.HttpHeaderFactory
import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.model.DefaultHttpHeader
import net.xelnaga.httpimposter.model.RequestPattern
import spock.lang.Specification

class RequestPatternMarshallerSpec extends Specification {

    public static final LinkedHashMap<String, String> HEADER_MAP_1 = [name: 'Content-Type', value: 'text/banana']
    public static final LinkedHashMap<String, String> HEADER_MAP_2 = [name: 'Pineapple', value: 'Passionfruit']
    public static final LinkedHashMap<String, String> HEADER_MAP_3 = [name: 'Durian', value: 'Stinky']

    public static final DefaultHttpHeader HEADER_1 = new DefaultHttpHeader('Content-Type', 'text/banana')
    public static final DefaultHttpHeader HEADER_2 = new DefaultHttpHeader('Pineapple', 'Passionfruit')
    public static final DefaultHttpHeader HEADER_3 = new DefaultHttpHeader('Durian', 'Stinky')

    RequestPatternMarshaller marshaller

    HttpHeaderFilter mockHttpHeaderFilter
    HttpHeaderFactory mockHttpHeaderFactory

    void setup() {

        marshaller = new RequestPatternMarshaller()

        mockHttpHeaderFilter = Mock(HttpHeaderFilter)
        marshaller.filter = mockHttpHeaderFilter

        mockHttpHeaderFactory = Mock(HttpHeaderFactory)
        marshaller.headerFactory = mockHttpHeaderFactory
    }

    def 'from json'() {

        given:
            Map json = [
                    headers: [
                        HEADER_MAP_1,
                        HEADER_MAP_2,
                        HEADER_MAP_3
                    ],
                    uri: '/fruity/pineapple',
                    body: 'Ym9keXRlc3Q=',
                    method: 'mango'
            ]

        when:
            RequestPattern requestPattern = marshaller.fromJson(json)

        then:
            1 * mockHttpHeaderFactory.makeHeader(HEADER_MAP_1) >> HEADER_1
            1 * mockHttpHeaderFactory.makeHeader(HEADER_MAP_2) >> HEADER_2
            1 * mockHttpHeaderFactory.makeHeader(HEADER_MAP_3) >> HEADER_3

            1 * mockHttpHeaderFilter.isMatchable(HEADER_1) >> true
            1 * mockHttpHeaderFilter.isMatchable(HEADER_2) >> true
            1 * mockHttpHeaderFilter.isMatchable(HEADER_3) >> false

            0 * _._

        and:
            requestPattern == new RequestPattern(
                    uri: '/fruity/pineapple',
                    method: 'mango',
                    headers: [
                        HEADER_1,
                        HEADER_2
                    ],
                    body: 'bodytest')
    }
}

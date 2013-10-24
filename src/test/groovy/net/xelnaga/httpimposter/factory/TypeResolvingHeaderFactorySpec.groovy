package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.DefaultHttpHeader
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.RegexMatchingHttpHeader
import spock.lang.Specification

class TypeResolvingHeaderFactorySpec extends Specification {

    HttpHeaderFactory factory

    void setup() {
        factory = new TypeResolvingHeaderFactory()
    }

    def 'make default header'() {
        given:
            Map<String, String> headerMap = [
                name: 'name',
                value: 'value'
            ]

        when:
            HttpHeader header = factory.makeHeader(headerMap)

        then:
            header instanceof DefaultHttpHeader
            header.name == headerMap.name
            header.value == headerMap.value
    }

    def 'make regex header'() {
        given:
            Map<String, String> headerMap = [
                name: 'name',
                value: 'value',
                type: 'regex'
            ]

        when:
            HttpHeader header = factory.makeHeader(headerMap)

        then:
            header instanceof RegexMatchingHttpHeader
            header.name == headerMap.name
            header.value == headerMap.value
            header.type == headerMap.type
    }
}

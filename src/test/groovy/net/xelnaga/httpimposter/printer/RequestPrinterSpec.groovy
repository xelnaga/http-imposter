package net.xelnaga.httpimposter.printer

import net.xelnaga.httpimposter.model.DefaultHttpHeader
import net.xelnaga.httpimposter.model.RequestPattern
import spock.lang.Specification

class RequestPrinterSpec extends Specification {

    RequestPrinter printer

    void setup() {
        printer = new RequestPrinter()
    }

    def 'print without headers or body'() {

        given:
            RequestPattern request = new RequestPattern(
                    method: 'POST',
                    uri: '/someuri',
                    headers:  [],
                    body: ''
            )

        when:
            String result = printer.print(request)

        then:
            result == '''\
POST /someuri
'''
    }

    def 'print with headers and without body'() {

        given:
            RequestPattern request = new RequestPattern(
                    method: 'GET',
                    uri: '/someuri',
                    headers:  [
                            new DefaultHttpHeader('someheader1', 'somevalue1'),
                            new DefaultHttpHeader('someheader2', 'somevalue2')
                    ],
                    body: ''
            )

        when:
            String result = printer.print(request)

        then:
            result == '''\
GET /someuri

someheader1: somevalue1
someheader2: somevalue2
'''
    }

    def 'print without headers and with body'() {

        given:
            RequestPattern request = new RequestPattern(
                    method: 'GET',
                    uri: '/someuri',
                    headers: [],
                    body:  """\
the quick brown fox jumped over
the lazy dog"""
            )

        when:
            String result = printer.print(request)

        then:
            result == '''GET /someuri

the quick brown fox jumped over
the lazy dog
'''
    }

    def 'print with headers and with body'() {

        given:
            RequestPattern request = new RequestPattern(
                    method: 'POST',
                    uri: '/someuri',
                    headers: [
                            new DefaultHttpHeader('someheader1', 'somevalue1'),
                            new DefaultHttpHeader('someheader2', 'somevalue2')
                    ],
                    body: """\
the quick brown fox jumped over
the lazy dog"""
        )

        when:
            String result = printer.print(request)

        then:
            result == '''\
POST /someuri

someheader1: somevalue1
someheader2: somevalue2

the quick brown fox jumped over
the lazy dog
'''
    }

}

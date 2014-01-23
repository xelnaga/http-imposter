package net.xelnaga.httpimposter.printer

import net.xelnaga.httpimposter.model.DefaultHttpHeader
import net.xelnaga.httpimposter.model.DefaultBody
import net.xelnaga.httpimposter.model.RequestPattern
import spock.lang.Specification

class RequestPrinterSpec extends Specification {

    private static final DefaultBody EMPTY_REQUEST_BODY = new DefaultBody('')

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
                    body: EMPTY_REQUEST_BODY
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
                    body: EMPTY_REQUEST_BODY
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
                    body:   new DefaultBody("""\
the quick brown fox jumped over
the lazy dog"""
            ))

        when:
            String result = printer.print(request)

        then:
            result == '''GET /someuri

type: 'default'
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
                    body:  new DefaultBody("""\
the quick brown fox jumped over
the lazy dog"""
        ))

        when:
            String result = printer.print(request)

        then:
            result == '''\
POST /someuri

someheader1: somevalue1
someheader2: somevalue2

type: 'default'
the quick brown fox jumped over
the lazy dog
'''
    }
}

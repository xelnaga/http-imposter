package net.xelnaga.httpimposter.printer

import net.xelnaga.httpimposter.model.DefaultHttpHeader
import net.xelnaga.httpimposter.model.ResponsePreset
import spock.lang.Specification

class ResponsePrinterSpec extends Specification {

    ResponsePrinter printer

    void setup() {
        printer = new ResponsePrinter()
    }

    def 'print without headers or body'() {

        given:
            ResponsePreset response = new ResponsePreset(
                    status: 201,
                    headers:  [],
                    body: ''
            )

        when:
            String result = printer.print(response)

        then:
            result == '''\
201
'''
    }

    def 'print with headers and without body'() {

        given:
            ResponsePreset response = new ResponsePreset(
                    status: 201,
                    headers:  [
                            new DefaultHttpHeader('someheader1', 'somevalue1'),
                            new DefaultHttpHeader('someheader2', 'somevalue2')
                    ],
                    body: ''
            )

        when:
            String result = printer.print(response)

        then:
            result == '''\
201

someheader1: somevalue1
someheader2: somevalue2
'''
    }

    def 'print without headers and with body'() {

        given:
            ResponsePreset response = new ResponsePreset(
                    status: 201,
                    headers: [],
                    body:  """\
the quick brown fox jumped over
the lazy dog"""
            )

        when:
            String result = printer.print(response)

        then:
            result == '''\
201

the quick brown fox jumped over
the lazy dog
'''
    }

    def 'print with headers and with body'() {

        given:
            ResponsePreset response = new ResponsePreset(
                    status: 201,
                    headers: [
                            new DefaultHttpHeader('someheader1', 'somevalue1'),
                            new DefaultHttpHeader('someheader2', 'somevalue2')
                    ],
                    body: """\
the quick brown fox jumped over
the lazy dog"""
        )

        when:
            String result = printer.print(response)

        then:
            result == '''\
201

someheader1: somevalue1
someheader2: somevalue2

the quick brown fox jumped over
the lazy dog
'''
    }

}

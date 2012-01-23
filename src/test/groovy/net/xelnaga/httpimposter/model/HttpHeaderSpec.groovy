package net.xelnaga.httpimposter.model

import spock.lang.Specification

class HttpHeaderSpec extends Specification {

    def 'equals and hashcode'() {
    
        given:
            HttpHeader header1 = new HttpHeader(name1, value1)
            HttpHeader header2 = new HttpHeader(name2, value2)
        
        expect:
            header1.equals(header2) == result
            (header1.hashCode() == header2.hashCode()) == result

        where:
            name1          | name2          | value1       | value2       | result
            'Content-Type' | 'Content-Type' | 'text/plain' | 'text/plain' | true
            'Content-Type' | 'content-type' | 'text/plain' | 'text/plain' | true
            'Content-Type' | 'Content-Type' | 'Text/Plain' | 'text/plain' | false
            'Content-Type' | 'content-type' | 'Text/Plain' | 'text/plain' | false
    }
    
    def 'equals with same instance'() {
        
        given:
            HttpHeader httpHeader = new HttpHeader('Hello', 'world')
        
        expect:
            httpHeader.equals(httpHeader)
    }
    
    def 'equals with object of another type'() {
        
        given:
            HttpHeader httpHeader = new HttpHeader('Hello', 'world')
            String value = 'qwerty'
        
        expect:
            !httpHeader.equals(value)
    }
    
    def 'to string'() {
        
        given:
            HttpHeader httpHeader = new HttpHeader('Some-Name', 'some-value')
            
        expect:
            httpHeader.toString() == 'Some-Name: some-value'
    }
    
    def 'compare to'() {
        
        given:
            List<HttpHeader> headers = [
                    new HttpHeader('Content-Type', 'text/plain'),
                    new HttpHeader('content-type', 'text/plain'),
                    new HttpHeader('Accept', 'application/json'),
                    new HttpHeader('Accept', 'text/plain')
            ]
        
        when:
            TreeSet<HttpHeader> treeSet = [] as TreeSet
            headers.each { HttpHeader httpHeader ->
                treeSet << httpHeader    
            }
        
        then:
            treeSet as List == [ headers[2], headers[3], headers[1] ]
    }
}

package net.xelnaga.httpimposter.model

import spock.lang.Specification
import spock.lang.Unroll

class HttpHeaderSpec extends Specification {

    @Unroll
    def 'equals and hashcode'() {
    
        given:
            DefaultHttpHeader header1 = new DefaultHttpHeader(name1, value1)
            DefaultHttpHeader header2 = new DefaultHttpHeader(name2, value2)
        
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
            DefaultHttpHeader httpHeader = new DefaultHttpHeader('Hello', 'world')
        
        expect:
            httpHeader.equals(httpHeader)
    }
    
    def 'equals with object of another type'() {
        
        given:
            DefaultHttpHeader httpHeader = new DefaultHttpHeader('Hello', 'world')
            String value = 'qwerty'
        
        expect:
            !httpHeader.equals(value)
    }

    def 'equals with identical implementations and matching values (default)'() {
        given:
            def obj1 = new DefaultHttpHeader('name', 'expression')
            def obj2 = new DefaultHttpHeader('name', 'expression')

        expect:
            obj1.equals(obj2)
            obj2.equals(obj1)
    }

    def 'equals with identical implementations and matching values (regex)'() {
        given:
            def obj1 = new RegexMatchingHttpHeader('name', 'expression')
            def obj2 = new RegexMatchingHttpHeader('name', 'expression')

        expect:
            obj1.equals(obj2)
            obj2.equals(obj1)
    }

    def 'equals with different implementations and matching values'() {
        given:
            def obj1 = new RegexMatchingHttpHeader('name', 'expression')
            def obj2 = new DefaultHttpHeader('name', 'expression')

        expect:
            !obj1.equals(obj2)
            !obj2.equals(obj1)
    }

    def 'compareTo using different instances'() {
        given:
            def obj1 = new RegexMatchingHttpHeader('name', 'expression')
            def obj2 = new DefaultHttpHeader('name', 'expression')

        expect:
            obj1.compareTo(obj2) == 0
            obj2.compareTo(obj1) != 0
    }

    def 'to string'() {
        
        given:
            DefaultHttpHeader httpHeader = new DefaultHttpHeader('Some-Name', 'some-value')
            
        expect:
            httpHeader.toString() == 'Some-Name: some-value'
    }
    
    def 'compare to (default)'() {
        
        given:
            List<HttpHeader> headers = [
                new DefaultHttpHeader('Content-Type', 'text/plain'),
                new DefaultHttpHeader('content-type', 'text/plain'),
                new DefaultHttpHeader('Accept', 'application/json'),
                new DefaultHttpHeader('Accept', 'text/plain')
            ]
        
        when:
            TreeSet<HttpHeader> treeSet = [] as TreeSet
            headers.each { HttpHeader httpHeader ->
                treeSet << httpHeader    
            }
        
        then:
            treeSet as List == [ headers[2], headers[3], headers[1] ]
    }

    def 'compare to (regex)'() {
        given:
            List<HttpHeader> headers = [
                new RegexMatchingHttpHeader('Content-Type', 'text/plain'),
                new RegexMatchingHttpHeader('content-type', 'text/plain'),
                new RegexMatchingHttpHeader('Accept', 'application/json'),
                new RegexMatchingHttpHeader('Accept', 'text/plain')
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

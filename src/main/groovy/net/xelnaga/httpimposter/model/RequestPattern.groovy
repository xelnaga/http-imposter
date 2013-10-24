package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class RequestPattern {

    String method
    String uri
    Set<HttpHeader> headers = [] as TreeSet
    String body

    boolean matches(RequestPattern other) {

        if (method != other.method) { return false }
        if (uri != other.uri) { return false }
        if (body != other.body) { return false }

        if (headers.size() != other.headers.size()) { return false }

        def matchedHeaders = headers.findAll{ HttpHeader httpHeader ->
            return other.headers.find{ HttpHeader otherHttpHeader ->
                return httpHeader.compareTo(otherHttpHeader) == 0
            }
        }
        return matchedHeaders.size() == headers.size()
    }
}

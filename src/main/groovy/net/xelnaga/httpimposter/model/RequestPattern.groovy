package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class RequestPattern {

    String method
    String uri
    Set<HttpHeader> headers = [] as TreeSet
    String body
}

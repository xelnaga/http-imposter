package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class ResponsePreset {

    int status
    Set<HttpHeader> headers = [] as TreeSet
    Body body
}

package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode

import net.xelnaga.httpimposter.printer.RequestPatternPrinter

@EqualsAndHashCode
class RequestPattern {

    String method
    String uri
    Set<HttpHeader> headers = [] as TreeSet
    String body

    @Override
    String toString() {

        RequestPatternPrinter printer = new RequestPatternPrinter()
        return printer.print(this)
    }
}

package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode

import net.xelnaga.httpimposter.printer.RequestPatternPrinter

@EqualsAndHashCode
class RequestPattern {

    String uri
    String method
    Set<HttpHeader> headers = [] as TreeSet
    String body

    @Override
    String toString() {

        RequestPatternPrinter printer = new RequestPatternPrinter()
        return printer.print(this)
    }
}

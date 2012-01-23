package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode
import net.xelnaga.httpimposter.ImposterPrinter

@EqualsAndHashCode
class ImposterRequest {

    String uri
    String method
    Set<HttpHeader> headers = [] as TreeSet
    String body

    @Override
    String toString() {
        return new ImposterPrinter().print(this)
    }
}

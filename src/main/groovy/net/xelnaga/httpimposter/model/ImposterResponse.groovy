package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode
import net.xelnaga.httpimposter.StringPrinter

@EqualsAndHashCode
class ImposterResponse {

    int status
    Set<HttpHeader> headers = [] as TreeSet
    String body
    
    @Override
    String toString() {
        return new StringPrinter().print(this)
    }
}

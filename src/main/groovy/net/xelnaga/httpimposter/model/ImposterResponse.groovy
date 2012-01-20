package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode
import net.xelnaga.httpimposter.ImposterPrinter

@EqualsAndHashCode
class ImposterResponse {

    int status
    Map<String, String> headers = [:]
    String body
    
    @Override
    String toString() {
        return new ImposterPrinter().print(this)
    }
}

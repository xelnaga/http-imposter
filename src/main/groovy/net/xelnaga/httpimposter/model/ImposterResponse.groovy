package net.xelnaga.httpimposter.model

import groovy.transform.TupleConstructor
import groovy.transform.ToString

@TupleConstructor
@ToString
class ImposterResponse {

    int status
    String mime
    String body
}

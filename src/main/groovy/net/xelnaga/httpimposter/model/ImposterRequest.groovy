package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class ImposterRequest {

    String uri
    String method
    String mime
    String body
}

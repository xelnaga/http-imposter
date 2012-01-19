package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class ImposterRequest {

    String uri
    String method
    String mime
    String body
}

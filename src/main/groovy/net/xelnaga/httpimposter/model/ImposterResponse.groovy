package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class ImposterResponse {

    int status
    String mime
    String body
}

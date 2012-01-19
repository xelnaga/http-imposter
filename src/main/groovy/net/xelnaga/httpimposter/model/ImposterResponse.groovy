package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class ImposterResponse {

    int status
    String mime
    String body
}

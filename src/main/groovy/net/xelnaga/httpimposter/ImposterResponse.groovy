package net.xelnaga.httpimposter

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class ImposterResponse {

    int statusCode
    String contentType
    String responseBody
}

package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class ImposterResponse {

    int statusCode
    String contentType
    String responseBody
}

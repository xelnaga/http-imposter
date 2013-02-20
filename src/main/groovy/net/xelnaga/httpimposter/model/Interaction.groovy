package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class Interaction {

    final RequestPattern request
    final ResponsePreset response

    Interaction(RequestPattern request, ResponsePreset response) {

        this.request = request
        this.response = response
    }
}

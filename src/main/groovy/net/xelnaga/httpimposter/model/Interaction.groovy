package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class Interaction {

    RequestPattern requestPattern
    ResponsePreset responsePreset

    Integer expected
    Integer actual
}

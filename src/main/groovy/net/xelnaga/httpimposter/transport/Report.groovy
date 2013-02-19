package net.xelnaga.httpimposter.transport

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.model.RequestPattern

@EqualsAndHashCode
@ToString
class Report {

    List<Interaction> expectations
    List<RequestPattern> interactions
}

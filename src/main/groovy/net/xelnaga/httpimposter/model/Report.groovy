package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class Report {

    final List<Interaction> expectations
    final List<Interaction> interactions

    Report(List<Interaction> expectations, List<Interaction> interactions) {

        this.expectations = expectations
        this.interactions = interactions
    }
}

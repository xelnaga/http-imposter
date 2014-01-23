package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.InheritConstructors

@InheritConstructors
@EqualsAndHashCode(callSuper = true)
class DefaultBody extends BaseBody {

    @Override
    boolean matches(Body other) {
        return value == other.value
    }

    @Override
    String getType() {
        return BodyTypes.DEFAULT
    }
}

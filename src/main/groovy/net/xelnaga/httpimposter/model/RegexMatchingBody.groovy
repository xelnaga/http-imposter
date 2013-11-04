package net.xelnaga.httpimposter.model

import groovy.transform.InheritConstructors

@InheritConstructors
class RegexMatchingBody extends BaseBody {

    @Override
    boolean matches(Body requestBody) {
        return requestBody.value ==~ this.value
    }

    @Override
    String getType() {
        return BodyTypes.REGEX
    }
}

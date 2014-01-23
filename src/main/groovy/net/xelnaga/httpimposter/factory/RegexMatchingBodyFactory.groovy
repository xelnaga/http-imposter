package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.Body
import net.xelnaga.httpimposter.model.RegexMatchingBody

class RegexMatchingBodyFactory implements BodyFactory {
    @Override
    Body makeBody(Map<String, String> bodyMap) {
        return new RegexMatchingBody(bodyMap.value)
    }
}

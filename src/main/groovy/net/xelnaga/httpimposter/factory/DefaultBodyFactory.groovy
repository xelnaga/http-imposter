package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.DefaultBody
import net.xelnaga.httpimposter.model.Body

class DefaultBodyFactory implements BodyFactory {

    @Override
    Body makeBody(Map<String, String> bodyMap) {
        return new DefaultBody(bodyMap.value)
    }
}

package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.Body
import net.xelnaga.httpimposter.model.BodyTypes

class TypeResolvingBodyFactory implements BodyFactory{

    private static final String TYPE_NAME = "type"

    private static final DefaultBodyFactory DEFAULT_BODY_FACTORY = new DefaultBodyFactory()

    Map<String, BodyFactory> bodyFactoriesMap = [
        (BodyTypes.REGEX): new RegexMatchingBodyFactory()
    ]

    Body makeBody(Map<String, String> bodyMap) {

        BodyFactory bodyFactory = resolveType(bodyMap)
        Body body = bodyFactory.makeBody(bodyMap)

        return body
    }

    private BodyFactory resolveType(bodyMap){
        String bodyType = bodyMap.get(TYPE_NAME)

        BodyFactory factory = bodyFactoriesMap[(bodyType)]

        if (!factory) {
            return DEFAULT_BODY_FACTORY
        }

        return factory
    }

}

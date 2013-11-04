package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.Body

interface BodyFactory {
    Body makeBody(Map<String, String> bodyMap)
}

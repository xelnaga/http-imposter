package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.ImposterRequest
import net.xelnaga.httpimposter.model.ImposterResponse
import groovy.json.JsonSlurper

class ImposterJsonParser {

    ImposterRequest parseRequest(String json) {
        return new JsonSlurper().parseText(json) as ImposterRequest
    }

    ImposterResponse parseResponse(String json) {
        return new JsonSlurper().parseText(json) as ImposterResponse
    }
}
package net.xelnaga.httpimposter

import spock.lang.Specification
import net.xelnaga.httpimposter.model.ImposterRequest
import net.xelnaga.httpimposter.model.ImposterResponse
import groovy.json.JsonOutput

class ImposterJsonParserSpec extends Specification {

    private ImposterJsonParser parser
    
    void setup() {
        parser = new ImposterJsonParser()
    }

    def 'parse request'() {

        given:
            String json = '''{
    "headers": {
        "Content-Type": "text/banana",
        "Pineapple": "Passionfruit"
    },
    "uri": "/fruity/pineapple",
    "body": "qwerty",
    "method": "mango"
}'''

        expect:
            parser.parseRequest(json) == new ImposterRequest(
                uri: '/fruity/pineapple',
                method: 'mango',
                headers: [
                        'Content-Type': 'text/banana',
                        'Pineapple': 'Passionfruit'
                ],
                body: 'qwerty')
    }
    
    def 'parse response'() {
        
        given:
            String json = '''{
    "headers": {
        "Content-Type": "text/exciting",
        "Lemon": "Lime"
    },
    "status": 234,
    "body": "qwertyuiop"
}'''
        
        expect:
            parser.parseResponse(json) == new ImposterResponse(
                    status: 234,
                    headers: [
                            'Content-Type': 'text/exciting',
                            'Lemon': 'Lime'
                    ],
                    body: 'qwertyuiop'
            )
    }
}

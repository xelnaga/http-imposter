package net.xelnaga.httpimposter.serializer.json

import net.xelnaga.httpimposter.SpecificationHelper
import net.xelnaga.httpimposter.model.Interaction
import spock.lang.Specification

class JsonInteractionSerializerSpec extends Specification {

    JsonInteractionSerializer serializer

    SpecificationHelper helper

    Interaction interaction
    String serialized

    void setup() {

        serializer = new JsonInteractionSerializer()

        helper = new SpecificationHelper()

        interaction = helper.makeInteraction(1)
        serialized = '''{"request":{"method":"somemethod1","uri":"/someuri1","headers":[{"name":"headername1","value":"headervalue1","type":"default"}],"body":{"value":"cmVxdWVzdEJvZHkx","type":"default"}},"response":{"status":201,"headers":[{"name":"headername1","value":"headervalue1","type":"default"}],"body":{"value":"cmVzcG9uc2VCb2R5MQ==","type":"default"}}}'''
    }

    def 'serialize'() {

        when:
            String result = serializer.serialize(interaction)

        then:
            result == serialized
    }

    def 'deserialize'() {

        when:
            Interaction result = serializer.deserialize(serialized)

        then:
            result == interaction
    }
}

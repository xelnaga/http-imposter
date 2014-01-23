package net.xelnaga.httpimposter.serializer.json

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import net.xelnaga.httpimposter.factory.TypeResolvingBodyFactory
import net.xelnaga.httpimposter.model.DefaultBody
import net.xelnaga.httpimposter.model.Body
import net.xelnaga.httpimposter.model.BodyTypes
import spock.lang.Specification

class BodyAdapterSpec extends Specification {

    private static final String ENCODED_TEST_VALUE = "ZGVjb2RlZA=="
    private static final String DECODED_TEST_VALUE = "decoded"

    private static final String TEST_VALUE_NAME = "value"
    private static final String TEST_TYPE_NAME = "type"

    BodyAdapter bodyAdapter = new BodyAdapter()

    def "body object is null"() {

        given:
            JsonWriter writer = Mock(JsonWriter)
            Body body = null

        when:
            bodyAdapter.write(writer, body)

        then:
            1 * writer.nullValue()
            0 * _
    }

    def "generate body"() {

        given:
            JsonWriter writer = Mock(JsonWriter)
            Body mockBody = Mock(Body)

        when:
            bodyAdapter.write(writer, mockBody)

        then:
            1 * writer.beginObject()
            1 * writer.name(TEST_VALUE_NAME)
            1 * mockBody.getValue() >> DECODED_TEST_VALUE
            1 * writer.value(ENCODED_TEST_VALUE)
            1 * writer.name(TEST_TYPE_NAME)
            1 * mockBody.getType() >> BodyTypes.DEFAULT
            1 * writer.value(BodyTypes.DEFAULT)
            1 * writer.endObject()
            0 * _
    }

    def "reading object: if reader object is null"() {

        given:
            JsonReader jsonReader = Mock(JsonReader)

        when:
            Body body = bodyAdapter.read(jsonReader)

        then:
            1 * jsonReader.peek() >> JsonToken.NULL
            1 * jsonReader.nextNull()
            0 * _

        and:
            body == null
    }

    def "reading body"() {

        given:
            JsonReader jsonReader = Mock(JsonReader)
            bodyAdapter.bodyFactory = Mock(TypeResolvingBodyFactory)
            Body expectedBody = new DefaultBody(DECODED_TEST_VALUE)

        when:
            Body body = bodyAdapter.read(jsonReader)

        then:
            1 * jsonReader.peek() >> JsonToken.NAME
            1 * jsonReader.beginObject()
            1 * jsonReader.hasNext() >> true
            1 * jsonReader.nextName() >> TEST_VALUE_NAME
            1 * jsonReader.nextString() >> ENCODED_TEST_VALUE

        then:
            1 * jsonReader.hasNext() >> false
            1 * jsonReader.endObject()

        and:
            1 * bodyAdapter.bodyFactory.makeBody((Map){ bodyMap ->
                bodyMap.value == DECODED_TEST_VALUE
            }) >> expectedBody
            0 * _

        and:
            body == expectedBody
    }
}

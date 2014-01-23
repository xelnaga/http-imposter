package net.xelnaga.httpimposter.serializer.json

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import net.xelnaga.httpimposter.factory.TypeResolvingBodyFactory
import net.xelnaga.httpimposter.model.Body
import org.apache.commons.codec.binary.Base64

class BodyAdapter extends TypeAdapter<Body> {

    private static final String VALUE_KEY_NAME = 'value'
    private static final String TYPE_KEY_NAME = 'type'

    TypeResolvingBodyFactory bodyFactory = new TypeResolvingBodyFactory()

    @Override
    void write(JsonWriter writer, Body requestBody) throws IOException {
        if (requestBody == null) {
            writer.nullValue()
            return
        }

        String rawBody = requestBody.getValue()
        String encodedBody = Base64.encodeBase64String(rawBody.bytes)

        writer.beginObject()
        writer.name(VALUE_KEY_NAME)
        writer.value(encodedBody)
        writer.name(TYPE_KEY_NAME)
        writer.value(requestBody.getType())
        writer.endObject()
    }

    @Override
    public Body read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return null
        }

        Map transportFormattedParams = generateParams(reader)
        Map params = parseTransportFormattedParams(transportFormattedParams)
        validateParams(params)

        Body requestBody = bodyFactory.makeBody(params)

        return requestBody
    }

    private Map<String, String> parseTransportFormattedParams(Map<String, String> map) {
        map.collectEntries { String key, String value ->
            String selectedValue = value
            if (shouldDecode(key)) {
                String decodedValue = new String(Base64.decodeBase64(value))
                selectedValue = decodedValue
            }
            return [(key) : selectedValue]

        } as Map<String, String>
    }

    private boolean shouldDecode(String key) {
        return key == VALUE_KEY_NAME
    }

    private Map generateParams(JsonReader reader) {
        Map params = [:]

        reader.beginObject()

        addAllParams(reader, params)

        reader.endObject()

        return params
    }

    private void addAllParams(JsonReader reader, Map params) {
        while(reader.hasNext()) {
            addNextParam(reader, params)
        }
    }

    private void addNextParam(JsonReader reader, Map params) {
        String elementName = reader.nextName()
        String elementValue = reader.nextString()
        params[elementName] = elementValue
    }

    private void validateParams(Map params) {
        if (!params.keySet().containsAll(['value'])) {
            throw new IOException("expected value key, keys: '${params}'")
        }
    }


}

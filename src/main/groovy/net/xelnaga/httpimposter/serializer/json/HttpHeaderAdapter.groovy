package net.xelnaga.httpimposter.serializer.json

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import net.xelnaga.httpimposter.factory.HttpHeaderFactory
import net.xelnaga.httpimposter.factory.TypeResolvingHeaderFactory
import net.xelnaga.httpimposter.model.HttpHeader

class HttpHeaderAdapter extends TypeAdapter<HttpHeader> {

    private static final String NAME_KEY_NAME = 'name'
    private static final String VALUE_KEY_NAME = 'value'
    private static final String TYPE_KEY_NAME = 'type'

    HttpHeaderFactory headerFactory = new TypeResolvingHeaderFactory()

    public HttpHeader read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return null
        }

        Map params = generateParams(reader)
        validateParams(params)
        HttpHeader httpHeader = headerFactory.makeHeader(params)
        return httpHeader
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
        if (!params.keySet().containsAll(['name', 'value'])) {
            throw new IOException("expected name and value keys, keys: '${params}'")
        }
    }

    public void write(JsonWriter writer, HttpHeader header) throws IOException {
        if (header == null) {
            writer.nullValue()
            return
        }
        writer.beginObject()
        writer.name(NAME_KEY_NAME)
        writer.value(header.name)
        writer.name(VALUE_KEY_NAME)
        writer.value(header.value)
        writer.name(TYPE_KEY_NAME)
        writer.value(header.type)
        writer.endObject()
    }
}

package net.xelnaga.httpimposter.serializer.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import net.xelnaga.httpimposter.model.Body
import net.xelnaga.httpimposter.model.HttpHeader


class ModelAwareJsonSerializerFactory {

    final Gson serializer

    ModelAwareJsonSerializerFactory() {
        serializer = createSerializer()
    }

    private Gson createSerializer() {
        return new GsonBuilder()
            .registerTypeAdapter(HttpHeader.class, new HttpHeaderAdapter())
            .registerTypeAdapter(Body.class, new BodyAdapter())
            .disableHtmlEscaping()
            .create()
    }
}

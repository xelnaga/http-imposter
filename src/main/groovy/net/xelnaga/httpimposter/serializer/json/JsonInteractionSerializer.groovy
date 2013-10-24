package net.xelnaga.httpimposter.serializer.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.serializer.InteractionSerializer

class JsonInteractionSerializer implements InteractionSerializer {

    private Gson gson = new GsonBuilder().registerTypeAdapter(HttpHeader.class, new HttpHeaderAdapter()).create()

    @Override
    String serialize(Interaction interaction) {
        return gson.toJson(interaction)
    }

    @Override
    Interaction deserialize(String interaction) {
        return gson.fromJson(interaction, Interaction)
    }
}

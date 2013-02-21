package net.xelnaga.httpimposter.serializer.json

import com.google.gson.Gson
import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.serializer.InteractionSerializer

class JsonInteractionSerializer implements InteractionSerializer {

    private Gson gson = new Gson()

    @Override
    String serialize(Interaction interaction) {
        return gson.toJson(interaction)
    }

    @Override
    Interaction deserialize(String interaction) {
        return gson.fromJson(interaction, Interaction)
    }
}

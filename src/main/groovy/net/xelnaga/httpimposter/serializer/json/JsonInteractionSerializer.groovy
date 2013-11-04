package net.xelnaga.httpimposter.serializer.json

import com.google.gson.Gson
import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.serializer.InteractionSerializer

class JsonInteractionSerializer implements InteractionSerializer {

    Gson serializer

    @Override
    String serialize(Interaction interaction) {
        return getSerializer().toJson(interaction)
    }

    @Override
    Interaction deserialize(String interaction) {
        return getSerializer().fromJson(interaction, Interaction)
    }

    Gson getSerializer() {
        if (!serializer) {
            ModelAwareJsonSerializerFactory factory = new ModelAwareJsonSerializerFactory()
            serializer = factory.serializer
        }
        return serializer
    }
}

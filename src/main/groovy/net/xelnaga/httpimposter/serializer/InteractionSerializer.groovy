package net.xelnaga.httpimposter.serializer

import net.xelnaga.httpimposter.model.Interaction

interface InteractionSerializer {

    String serialize(Interaction interaction)
    Interaction deserialize(String interaction)
}


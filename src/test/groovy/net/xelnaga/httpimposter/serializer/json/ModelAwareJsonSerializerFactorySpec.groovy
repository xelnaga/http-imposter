package net.xelnaga.httpimposter.serializer.json

import com.google.gson.Gson
import spock.lang.Specification


class ModelAwareJsonSerializerFactorySpec extends Specification {

    Gson serializer

    void setup() {
        serializer = new ModelAwareJsonSerializerFactory().serializer
    }

    def 'serialize with html characters'() {

        when:
            String result = serializer.toJson(['htmlchars': "<'=>"])

        then:
            result == '''{"htmlchars":"<'=>"}'''
    }

    def 'deserialize with html characters'() {

        when:
            Map result = serializer.fromJson('''{"htmlchars":"<'=>"}''', Map)

        then:
            result == ['htmlchars': "<'=>"]
    }
}

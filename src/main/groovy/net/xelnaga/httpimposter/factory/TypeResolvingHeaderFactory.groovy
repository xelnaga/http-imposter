package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.HttpHeader

class TypeResolvingHeaderFactory implements HttpHeaderFactory {

    Map<String, HttpHeaderFactory> typeToFactoryMap = [
        'regex': new RegexMatchingHttpHeaderFactory()
    ]

    private static final HttpHeaderFactory DEFAULT_FACTORY = new DefaultHttpHeaderFactory()

    @Override
    HttpHeader makeHeader(Map<String, String> headerMap) {

        HttpHeaderFactory factory = resolveFactoryByType(headerMap.type)
        return factory.makeHeader(headerMap)
    }

    HttpHeaderFactory resolveFactoryByType(String type) {
        HttpHeaderFactory factory = typeToFactoryMap[type]
        if (!factory) {
            return DEFAULT_FACTORY
        }

        return factory
    }
}

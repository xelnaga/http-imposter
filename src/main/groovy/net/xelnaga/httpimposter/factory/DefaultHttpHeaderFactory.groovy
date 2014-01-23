package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.DefaultHttpHeader

class DefaultHttpHeaderFactory implements HttpHeaderFactory {
    @Override
    HttpHeader makeHeader(Map<String, String> headerMap) {
        return new DefaultHttpHeader(headerMap.name, headerMap.value)
    }
}

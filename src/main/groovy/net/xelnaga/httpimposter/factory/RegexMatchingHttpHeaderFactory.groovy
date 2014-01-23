package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.RegexMatchingHttpHeader

class RegexMatchingHttpHeaderFactory implements HttpHeaderFactory {
    @Override
    HttpHeader makeHeader(Map<String, String> headerMap) {
        return new RegexMatchingHttpHeader(headerMap.name, headerMap.value)
    }
}

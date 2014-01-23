package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.model.HttpHeader

interface HttpHeaderFactory {
    HttpHeader makeHeader(Map<String, String> headerMap)
}

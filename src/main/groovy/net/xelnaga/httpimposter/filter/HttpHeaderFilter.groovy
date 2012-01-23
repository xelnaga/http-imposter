package net.xelnaga.httpimposter.filter

import net.xelnaga.httpimposter.model.HttpHeader

interface HttpHeaderFilter {

    boolean isMatchable(HttpHeader httpHeader)
}
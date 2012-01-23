package net.xelnaga.httpimposter.filter

import net.xelnaga.httpimposter.model.HttpHeader

class PassThroughFilter implements HttpHeaderFilter {

    @Override
    boolean isMatchable(HttpHeader httpHeader) {
        return true
    }
}

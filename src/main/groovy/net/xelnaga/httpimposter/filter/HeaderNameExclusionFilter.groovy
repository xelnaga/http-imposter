package net.xelnaga.httpimposter.filter

import net.xelnaga.httpimposter.model.HttpHeader

class HeaderNameExclusionFilter implements HttpHeaderFilter {

    private Set<String> exclusions

    HeaderNameExclusionFilter(List<String> exclusions) {
        this.exclusions = exclusions.collect { it.toLowerCase() } as TreeSet
    }

    @Override
    boolean isMatchable(HttpHeader httpHeader) {
        return !exclusions.contains(httpHeader.name.toLowerCase())
    }
}

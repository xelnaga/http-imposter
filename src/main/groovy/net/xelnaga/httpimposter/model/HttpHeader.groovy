package net.xelnaga.httpimposter.model

interface HttpHeader extends Comparable<HttpHeader> {
    String getName()
    String getValue()

    // used by HttpHeaderFactory to create appropriate instances of HttpHeader
    String getType()

    int compareValue(String otherValue)
}

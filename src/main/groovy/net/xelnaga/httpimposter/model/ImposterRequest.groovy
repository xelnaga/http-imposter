package net.xelnaga.httpimposter.model

import net.xelnaga.httpimposter.StringPrinter
import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder


class ImposterRequest {

    String uri
    String method
    Set<HttpHeader> headers = [] as TreeSet
    String body

    @Override
    String toString() {
        StringPrinter printer = new StringPrinter()
        return printer.print(this)
    }


    @Override
    boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this)
    }


}

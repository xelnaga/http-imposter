package net.xelnaga.httpimposter.model

abstract class BaseHttpHeader implements HttpHeader {

    @Override
    boolean equals(Object obj) {

        if (this.is(obj)) {
            return true
        }

        if (!(obj instanceof HttpHeader)) {
            return false
        }

        HttpHeader that = (HttpHeader) obj

        return name.equalsIgnoreCase(that.name) && type == that.type && value == that.value
    }

    @Override
    int hashCode() {
        int result = name.toLowerCase().hashCode()
        return 31 * result + value.hashCode()
    }

    @Override
    String toString() {
        return "${name}: ${value}"
    }

}

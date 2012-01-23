package net.xelnaga.httpimposter.model

class HttpHeader implements Comparable {

    final String name
    final String value

    HttpHeader(String name, String value) {

        this.name = name
        this.value = value
    }

    @Override
    int compareTo(Object obj) {
        
        if (!(obj instanceof HttpHeader)) {
            return 1
        }
        
        HttpHeader that = (HttpHeader) obj

        int result = name.compareToIgnoreCase(that.name)
        if (result != 0) {
            return result
        }

        return value.compareTo(that.value)
    }

    @Override
    boolean equals(Object obj) {

        if (this.is(obj)) {
            return true
        }

        if (!(obj instanceof HttpHeader)) {
            return false
        }

        HttpHeader that = (HttpHeader) obj

        return name.equalsIgnoreCase(that.name) && value == that.value
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

package net.xelnaga.httpimposter.model

class DefaultHttpHeader extends BaseHttpHeader {

    final String name
    final String value

    DefaultHttpHeader(String name, String value) {
        this.name = name
        this.value = value
    }

    @Override
    int compareValue(String otherValue) {
        value.compareTo(otherValue)
    }

    @Override
    int compareTo(HttpHeader other) {

        int result = name.compareToIgnoreCase(other.name)
        if (result != 0) {
            return result
        }

        result = type.compareTo(other.type)
        if (result != 0) {
            return result
        }

        return compareValue(other.value)
    }

    @Override
    String getType() {
        return HttpHeaderTypes.DEFAULT
    }
}

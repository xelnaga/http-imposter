package net.xelnaga.httpimposter.model

class DefaultHttpHeader extends BaseHttpHeader {

    final String name
    final String value
    final String type = 'default'

    DefaultHttpHeader(String name, String value) {
        this.name = name
        this.value = value
    }

    @Override
    int compareValue(String otherValue) {
        value.compareTo(otherValue)
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

        result = type.compareTo(that.type)
        if (result != 0) {
            return result
        }

        return compareValue(that.value)
    }

}

package net.xelnaga.httpimposter.model

class RegexMatchingHttpHeader extends BaseHttpHeader {

    final String name
    final String value

    RegexMatchingHttpHeader(String name, String regularExpression) {
        this.name = name
        this.value = regularExpression
    }

    @Override
    int compareValue(String otherValue) {
        boolean matches = otherValue ==~ value
        return matches ? 0 : value.compareTo(otherValue)
    }

    @Override
    int compareTo(HttpHeader other) {
        int result = name.compareToIgnoreCase(other.name)
        if (result != 0) {
            return result
        }

        return compareValue(other.value)
    }

    @Override
    String getType() {
        return HttpHeaderTypes.REGEX
    }
}

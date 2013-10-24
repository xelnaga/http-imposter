package net.xelnaga.httpimposter.model

class RegexMatchingHttpHeader extends BaseHttpHeader {

    final String name
    final String value
    final String type = 'regex'

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
    int compareTo(Object obj) {
        if (!(obj instanceof HttpHeader)) {
            return 1
        }

        HttpHeader that = (HttpHeader) obj

        int result = name.compareToIgnoreCase(that.name)
        if (result != 0) {
            return result
        }

        return compareValue(that.value)
    }
}

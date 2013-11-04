package net.xelnaga.httpimposter.model

abstract class BaseBody implements Body {

    final String value

    BaseBody(String value) {
        this.value = value
    }

    @Override
    boolean equals(Object obj) {

        if (this.is(obj)) {
            return true
        }

        if (!(obj instanceof Body)) {
            return false
        }

        Body that = (Body) obj

        return type == that.type && value == that.value
    }

    @Override
    boolean isEmpty() {
        if(!value || value.size() == 0) {
            return true
        }
        return false
    }

    @Override
    String toString(){
        return "[type: '${type}', body: '${value}']"
    }

    @Override
    int hashCode() {
        int typeHashCode = type.hashCode()
        return 31 * typeHashCode + value.hashCode()
    }

    @Override
    int compareTo(Body other) {
        boolean matches = this.matches(other)
        return matches ? 0 : this.value.compareTo(other.value)
    }
}

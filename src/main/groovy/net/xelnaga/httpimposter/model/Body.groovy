package net.xelnaga.httpimposter.model

interface Body extends Comparable<Body> {
    boolean matches(Body requestBody)
    boolean isEmpty()
    String getType()
    String getValue()
}

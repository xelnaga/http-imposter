package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode
import net.xelnaga.httpimposter.printer.ResponsePresetPrinter

@EqualsAndHashCode
class ResponsePreset {

    int status
    Set<HttpHeader> headers = [] as TreeSet
    String body

    @Override
    String toString() {

        ResponsePresetPrinter printer = new ResponsePresetPrinter()
        return printer.print(this)
    }
}

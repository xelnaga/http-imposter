package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.printer.RequestPatternPrinter
import net.xelnaga.httpimposter.printer.ResponsePresetPrinter
import org.apache.log4j.Logger

class LogWriter {

    Logger log = Logger.getLogger(LogWriter)

    RequestPatternPrinter requestPatternPrinter
    ResponsePresetPrinter responsePresetPrinter

    void interact(Interaction interaction) {

        log.info '\n>> [Http Imposter]: Received request pattern'
        log.info '>> ==========================================='
        log.info requestPatternPrinter.print(interaction.request)
        log.info '>>'

        log.info '\n>> [Http Imposter]: Sending response preset'
        log.info '>> ==========================================='
        log.info responsePresetPrinter.print(interaction.response)
        log.info '>>'
    }
}

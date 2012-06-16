package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.ResponsePreset
import org.apache.log4j.Logger

class LogWriter {

    Logger log = Logger.getLogger(LogWriter)

    void interact(RequestPattern requestPattern, ResponsePreset responsePreset) {

        log.info '\n>> [Http Imposter]: Received request pattern'
        log.info '>> ==========================================='
        log.info requestPattern.toString()
        log.info '>>'

        log.info '\n>> [Http Imposter]: Sending response preset'
        log.info '>> ==========================================='
        log.info responsePreset.toString()
        log.info '>>'
    }
}

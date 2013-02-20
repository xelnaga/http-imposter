package net.xelnaga.httpimposter.serializer

import net.xelnaga.httpimposter.SpecificationHelper
import net.xelnaga.httpimposter.model.Report
import net.xelnaga.httpimposter.serialiser.JsonReportSerializer
import spock.lang.Specification

class JsonReportSerializerSpec extends Specification {

    JsonReportSerializer serializer

    SpecificationHelper helper

    Report report
    String serial

    void setup() {

        serializer = new JsonReportSerializer()
        helper = new SpecificationHelper()

        report = new Report(
                [helper.makeInteraction(1), helper.makeInteraction(2)],
                [helper.makeInteraction(3), helper.makeInteraction(4)]
        )

        serial = '''{"expectations":[{"request":{"method":"somemethod1","uri":"/someuri1","headers":[{"name":"headername1","value":"headervalue1"}],"body":"somebody1"},"response":{"status":201,"headers":[{"name":"headername1","value":"headervalue1"}],"body":"somebody1"}},{"request":{"method":"somemethod2","uri":"/someuri2","headers":[{"name":"headername2","value":"headervalue2"}],"body":"somebody2"},"response":{"status":202,"headers":[{"name":"headername2","value":"headervalue2"}],"body":"somebody2"}}],"interactions":[{"request":{"method":"somemethod3","uri":"/someuri3","headers":[{"name":"headername3","value":"headervalue3"}],"body":"somebody3"},"response":{"status":203,"headers":[{"name":"headername3","value":"headervalue3"}],"body":"somebody3"}},{"request":{"method":"somemethod4","uri":"/someuri4","headers":[{"name":"headername4","value":"headervalue4"}],"body":"somebody4"},"response":{"status":204,"headers":[{"name":"headername4","value":"headervalue4"}],"body":"somebody4"}}]}'''
    }

    def 'serialize'() {

        when:
            String result = serializer.serialize(report)

        then:
            result == serial
    }

    def 'deserialize'() {

        when:
            Report result = serializer.deserialize(serial)

        then:
            result == report
    }
}

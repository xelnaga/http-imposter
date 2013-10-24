package net.xelnaga.httpimposter.serializer.json

import net.xelnaga.httpimposter.SpecificationHelper
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.RegexMatchingHttpHeader
import net.xelnaga.httpimposter.model.Report
import spock.lang.Specification

class JsonReportSerializerSpec extends Specification {

    JsonReportSerializer serializer

    SpecificationHelper helper

    Report expectedReport
    static String serial

    void setup() {

        serializer = new JsonReportSerializer()

        helper = new SpecificationHelper()

        expectedReport = new Report(
                [helper.makeInteraction(1), helper.makeInteraction(2)],
                [helper.makeInteraction(3), helper.makeInteraction(4)]
        )

        serial = '''{"expectations":[{"request":{"method":"somemethod1","uri":"/someuri1","headers":[{"name":"headername1","value":"headervalue1"}],"body":"somebody1"},"response":{"status":201,"headers":[{"name":"headername1","value":"headervalue1"}],"body":"somebody1"}},{"request":{"method":"somemethod2","uri":"/someuri2","headers":[{"name":"headername2","value":"headervalue2"}],"body":"somebody2"},"response":{"status":202,"headers":[{"name":"headername2","value":"headervalue2"}],"body":"somebody2"}}],"interactions":[{"request":{"method":"somemethod3","uri":"/someuri3","headers":[{"name":"headername3","value":"headervalue3"}],"body":"somebody3"},"response":{"status":203,"headers":[{"name":"headername3","value":"headervalue3"}],"body":"somebody3"}},{"request":{"method":"somemethod4","uri":"/someuri4","headers":[{"name":"headername4","value":"headervalue4"}],"body":"somebody4"},"response":{"status":204,"headers":[{"name":"headername4","value":"headervalue4"}],"body":"somebody4"}}]}'''
    }

    def 'serialize'() {

        when:
            String result = serializer.serialize(expectedReport)

        then:
            result == serial
    }

    def 'deserialize report with default http header'() {

        when:
            Report report = serializer.deserialize(serial)

        then:
            report == expectedReport

    }

    def 'deserialize report with regex http header'() {
        given:
        String serialWithRegex = '''{"expectations":[{"request":{"method":"somemethod1","uri":"/someuri1","headers":[{"name":"headername1","value":".*","type":"regex"}],"body":"somebody1"},"response":{"status":201,"headers":[{"name":"headername1","value":".*","type":"regex"}],"body":"somebody1"}},{"request":{"method":"somemethod2","uri":"/someuri2","headers":[{"name":"headername2","value":"headervalue2"}],"body":"somebody2"},"response":{"status":202,"headers":[{"name":"headername2","value":"headervalue2"}],"body":"somebody2"}}],"interactions":[{"request":{"method":"somemethod3","uri":"/someuri3","headers":[{"name":"headername3","value":"headervalue3"}],"body":"somebody3"},"response":{"status":203,"headers":[{"name":"headername3","value":"headervalue3"}],"body":"somebody3"}},{"request":{"method":"somemethod4","uri":"/someuri4","headers":[{"name":"headername4","value":"headervalue4"}],"body":"somebody4"},"response":{"status":204,"headers":[{"name":"headername4","value":"headervalue4"}],"body":"somebody4"}}]}'''
        HttpHeader httpHeader1 = new RegexMatchingHttpHeader('headername1', '.*')
        Report expectedReport = new Report(
            [helper.makeInteraction(1, httpHeader1), helper.makeInteraction(2)],
            [helper.makeInteraction(3), helper.makeInteraction(4)]
        )

        when:
        Report report = serializer.deserialize(serialWithRegex)

        then:
        report == expectedReport
    }
}

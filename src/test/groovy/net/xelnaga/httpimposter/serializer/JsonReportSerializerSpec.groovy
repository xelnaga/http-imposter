package net.xelnaga.httpimposter.serializer

import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.Interaction
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.serialiser.JsonReportSerializer
import net.xelnaga.httpimposter.transport.Report
import spock.lang.Specification

class JsonReportSerializerSpec extends Specification {

    JsonReportSerializer serializer

    Report report
    String serial

    void setup() {
        serializer = new JsonReportSerializer()

        report = new Report(
                expectations: [
                        new RequestPattern(
                                uri: '/someuri1',
                                method: 'somemethod1',
                                headers: [
                                        new HttpHeader('headername1', 'headervalue1')
                                ],
                                body: 'somebody1'
                        ),
                        new RequestPattern(
                                uri: '/someuri2',
                                method: 'somemethod2',
                                headers: [
                                        new HttpHeader('headername2', 'headervalue2')
                                ],
                                body: 'somebody2'
                        ),
                ],
                interactions: [
                        new RequestPattern(
                                uri: '/someuri3',
                                method: 'somemethod3',
                                headers: [
                                        new HttpHeader('headername3', 'headervalue3')
                                ],
                                body: 'somebody3'
                        ),
                        new RequestPattern(
                                uri: '/someuri4',
                                method: 'somemethod4',
                                headers: [
                                        new HttpHeader('headername4', 'headervalue4')
                                ],
                                body: 'somebody4'
                        )
                ],
                legacy: [
                        new Interaction(
                                expected: 3,
                                actual: 5
                        )
                ]
        )

        serial = '''{"expectations":[{"uri":"/someuri1","method":"somemethod1","headers":[{"name":"headername1","value":"headervalue1"}],"body":"somebody1"},{"uri":"/someuri2","method":"somemethod2","headers":[{"name":"headername2","value":"headervalue2"}],"body":"somebody2"}],"interactions":[{"uri":"/someuri3","method":"somemethod3","headers":[{"name":"headername3","value":"headervalue3"}],"body":"somebody3"},{"uri":"/someuri4","method":"somemethod4","headers":[{"name":"headername4","value":"headervalue4"}],"body":"somebody4"}],"legacy":[{"expected":3,"actual":5}]}'''
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

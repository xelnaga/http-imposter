package net.xelnaga.httpimposter.serializer.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import net.xelnaga.httpimposter.SpecificationHelper
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.RegexMatchingHttpHeader
import net.xelnaga.httpimposter.model.RegexMatchingBody
import net.xelnaga.httpimposter.model.Report
import net.xelnaga.httpimposter.model.Body
import spock.lang.Specification

class JsonReportSerializerSpec extends Specification {

    JsonReportSerializer serializer

    SpecificationHelper helper

    Report report
    static String serializedReport

    void setup() {

        serializer = new JsonReportSerializer()

        helper = new SpecificationHelper()

        report = new Report(
                [helper.makeInteraction(1), helper.makeInteraction(2)],
                [helper.makeInteraction(3), helper.makeInteraction(4)]
        )


        Map<String, Object> serializedReportMap = [
            expectations: [
                [
                    request: [method: 'somemethod1', uri: '/someuri1', headers: [[name: 'headername1', value: 'headervalue1', type: 'default']], body: [value: 'cmVxdWVzdEJvZHkx', type: 'default']],
                    response: [status: 201, headers: [[name: 'headername1', value: 'headervalue1', type: 'default']], body: [value: 'cmVzcG9uc2VCb2R5MQ==', type: 'default']]
                ],
                [
                    request: [method: 'somemethod2', uri: '/someuri2', headers: [[name: 'headername2', value: 'headervalue2', type: 'default']], body: [value: 'cmVxdWVzdEJvZHky', type: 'default']],
                    response: [status: 202, headers: [[name: 'headername2', value: 'headervalue2', type: 'default']], body: [value: 'cmVzcG9uc2VCb2R5Mg==', type: 'default']]
                ]
            ],
            interactions: [
                [
                    request: [method: 'somemethod3', uri: '/someuri3', headers: [[name: 'headername3', value: 'headervalue3', type: 'default']], body: [value: 'cmVxdWVzdEJvZHkz', type: 'default']],
                    response: [status: 203, headers: [[name: 'headername3', value: 'headervalue3', type: 'default']], body: [value: 'cmVzcG9uc2VCb2R5Mw==', type: 'default']]
                ],
                [
                    request: [method: 'somemethod4', uri: '/someuri4', headers: [[name: 'headername4', value: 'headervalue4', type: 'default']], body: [value: 'cmVxdWVzdEJvZHk0', type: 'default']],
                    response: [status: 204, headers: [[name: 'headername4', value: 'headervalue4', type: 'default']], body: [value: 'cmVzcG9uc2VCb2R5NA==', type: 'default']]
                ]
            ]
        ]

        serializedReport = gson.toJson(serializedReportMap)
    }

    def 'serialize'() {

        when:
            String result = serializer.serialize(report)

        then:
            result == serializedReport
    }

    def 'deserialize report with default http header'() {

        when:
            Report report = serializer.deserialize(serializedReport)

        then:
            report == this.report
    }

    def 'deserialize report with regex http header and body'() {

        given:
            Map<String, Object> serializedReportMap = [
                expectations: [
                    [
                        request: [method: 'somemethod1', uri: '/someuri1', headers: [[name: 'headername1', value: '.*', type: 'regex']], body: [value: 'aS1sb3ZlLS4q', type: 'regex']],
                        response: [status: 201, headers: [[name: 'headername1', value: '.*', type: 'regex']], body: [value: 'cmVzcG9uc2VCb2R5MQ==', type: 'default']]
                    ]
                ],
                interactions: [
                    [
                        request: [method: 'somemethod3', uri: '/someuri3', headers: [[name: 'headername3', value: 'headervalue3', type: 'default']], body: [value: 'cmVxdWVzdEJvZHkz', type: 'default']],
                        response: [status: 203, headers: [[name: 'headername3', value: 'headervalue3', type: 'default']], body: [value: 'cmVzcG9uc2VCb2R5Mw==', type: 'default']]
                    ]
                ]
            ]

            String serializedReportWithRegex = gson.toJson(serializedReportMap)

            HttpHeader regexHttpHeader1 = new RegexMatchingHttpHeader('headername1', '.*')
            Body regexBody1 = new RegexMatchingBody('i-love-.*')

            Report expectedReport = new Report(
                [helper.makeInteraction(1, regexHttpHeader1, regexBody1)],
                [helper.makeInteraction(3)]
            )

        when:
            Report report = serializer.deserialize(serializedReportWithRegex)

        then:
            report == expectedReport
    }

    private Gson getGson() {
        new GsonBuilder().disableHtmlEscaping().create()
    }
}
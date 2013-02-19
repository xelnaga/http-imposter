package net.xelnaga.httpimposter.serialiser

import net.xelnaga.httpimposter.transport.Report

interface ReportSerializer {

    String serialize(Report report)
    Report deserialize(String report)
}

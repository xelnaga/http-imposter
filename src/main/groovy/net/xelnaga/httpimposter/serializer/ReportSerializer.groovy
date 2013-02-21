package net.xelnaga.httpimposter.serializer

import net.xelnaga.httpimposter.model.Report

interface ReportSerializer {

    String serialize(Report report)
    Report deserialize(String report)
}

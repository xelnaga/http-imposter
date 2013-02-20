package net.xelnaga.httpimposter.serialiser

import com.google.gson.Gson
import net.xelnaga.httpimposter.model.Report

class JsonReportSerializer implements ReportSerializer {

    private Gson gson = new Gson()

    @Override
    String serialize(Report report) {
        return gson.toJson(report)
    }

    @Override
    Report deserialize(String report) {
        return gson.fromJson(report, Report)
    }
}

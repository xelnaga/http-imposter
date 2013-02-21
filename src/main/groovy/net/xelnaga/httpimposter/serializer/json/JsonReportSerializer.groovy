package net.xelnaga.httpimposter.serializer.json

import com.google.gson.Gson
import net.xelnaga.httpimposter.model.Report
import net.xelnaga.httpimposter.serializer.ReportSerializer

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

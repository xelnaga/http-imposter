package net.xelnaga.httpimposter.serializer.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.Report
import net.xelnaga.httpimposter.serializer.ReportSerializer

class JsonReportSerializer implements ReportSerializer {

    private Gson gson = new GsonBuilder().registerTypeAdapter(HttpHeader.class, new HttpHeaderAdapter()).create()

    @Override
    String serialize(Report report) {
        return gson.toJson(report)
    }

    @Override
    Report deserialize(String report) {
        return gson.fromJson(report, Report)
    }
}

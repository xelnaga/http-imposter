package net.xelnaga.httpimposter.serializer.json

import com.google.gson.Gson
import net.xelnaga.httpimposter.model.Report
import net.xelnaga.httpimposter.serializer.ReportSerializer

class JsonReportSerializer implements ReportSerializer {

    Gson serializer

    @Override
    String serialize(Report report) {
        return getSerializer().toJson(report)
    }

    @Override
    Report deserialize(String report) {
        return getSerializer().fromJson(report, Report)
    }

    Gson getSerializer() {
        if (!serializer) {
            ModelAwareJsonSerializerFactory factory = new ModelAwareJsonSerializerFactory()
            serializer = factory.serializer
        }
        return serializer
    }
}

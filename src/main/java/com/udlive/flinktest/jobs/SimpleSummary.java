package com.udlive.flinktest.jobs;

import com.udlive.flinktest.model.Telemetry;
import com.udlive.flinktest.statistics.SummaryAccumulator;
import com.udlive.flinktest.statistics.SummaryAggregator;
import com.udlive.flinktest.streaming.TelemetryStreamController;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;

import java.time.Duration;

public class SimpleSummary {
    public static void main(String[] args) throws Exception {
        TelemetryStreamController streamController = new TelemetryStreamController();

        DataStream<Telemetry> parsedStream = streamController.createParsedStream();

        DataStream<SummaryAccumulator> summaryStream = parsedStream
                // Not sure keyBy is needed here
                .keyBy(value -> value)
                .windowAll(TumblingProcessingTimeWindows.of(Duration.ofSeconds(1)))
                .aggregate(new SummaryAggregator());

        summaryStream.print();
        streamController.startProcessing("Global Summary Job");
    }
}

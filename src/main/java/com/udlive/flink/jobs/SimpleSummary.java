package com.udlive.flink.jobs;

import com.udlive.flink.model.Telemetry;
import com.udlive.flink.statistics.SummaryAccumulator;
import com.udlive.flink.statistics.SummaryAggregator;
import com.udlive.flink.streaming.TelemetryStreamController;
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

package com.udlive.flink.jobs;

import com.udlive.flink.model.Telemetry;
import com.udlive.flink.statistics.SummaryAccumulator;
import com.udlive.flink.statistics.SummaryAggregator;
import com.udlive.flink.streaming.TelemetryStreamController;
import com.udlive.flink.utils.TelemetryUtils;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;

import java.time.Duration;

public class DailySummary {

    public static void main(String[] args) throws Exception {
        TelemetryStreamController streamController = new TelemetryStreamController();

        DataStream<Telemetry> parsedStream = streamController.createParsedStream();

        SingleOutputStreamOperator<SummaryAccumulator> summaryStream = parsedStream
                // I'm afraid this might be incorrect and I should use a watermark approach but after spending 6 hours trying to make watermarks work I gave up :(
                .keyBy(TelemetryUtils::getTimeStampFromTelemetry)
                .window(TumblingProcessingTimeWindows.of(Duration.ofDays(1)))
                .aggregate(new SummaryAggregator());

        summaryStream.print();

        streamController.startProcessing("Daily Summary Job");
    }
}

package com.udlive.flinktest.jobs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udlive.flinktest.model.Telemetry;
import com.udlive.flinktest.statistics.SummaryAccumulator;
import com.udlive.flinktest.statistics.SummaryAggregator;
import com.udlive.flinktest.streaming.TelemetryStreamController;
import com.udlive.flinktest.utils.FilePathUtils;
import com.udlive.flinktest.utils.TelemetryUtils;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;

import java.time.Duration;

public class DailySummary {

    public static void main(String[] args) throws Exception {
        TelemetryStreamController streamController = new TelemetryStreamController();

        DataStream<Telemetry> parsedStream = streamController.createParsedStream();

        DataStream<SummaryAccumulator> summaryStream = parsedStream
                .keyBy(TelemetryUtils::getKeyFromDate)
                .window(TumblingProcessingTimeWindows.of(Duration.ofDays(1)))
                .aggregate(new SummaryAggregator());

        summaryStream.print();

        streamController.startProcessing("Daily Summary Job");
    }
}

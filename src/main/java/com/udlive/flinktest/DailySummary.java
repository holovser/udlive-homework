package com.udlive.flinktest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udlive.flinktest.model.Telemetry;
import com.udlive.flinktest.statistics.simple.SummaryAccumulator;
import com.udlive.flinktest.statistics.simple.SummaryAggregator;
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

    static final String telemetryPath = "/Users/serhiiholovko/Downloads/flink_homework/flink_homework/resources/telemetry.dat";

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.BATCH);

        final FileSource<String> source =
                FileSource.forRecordStreamFormat(new TextLineInputFormat(), new Path(telemetryPath))
                        .build();

        DataStreamSource<String> stream =
                env.fromSource(source, WatermarkStrategy.noWatermarks(), "telemetry-file-source");

        SingleOutputStreamOperator<Telemetry> parsedStream = stream.map(jsonString -> {
            ObjectMapper objectMapper =
                    new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(jsonString, Telemetry.class);
        });


        DataStream<SummaryAccumulator> summaryStream = parsedStream
                .keyBy(TelemetryUtils::getKeyFromDate)
                .window(TumblingProcessingTimeWindows.of(Duration.ofDays(1)))
                .aggregate(new SummaryAggregator());


        summaryStream.print();
        env.execute("Daily Summary Job");

    }
}

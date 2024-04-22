package com.udlive.flinktest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udlive.flinktest.model.Telemetry;
import com.udlive.flinktest.statistics.SummaryAccumulator;
import com.udlive.flinktest.statistics.SummaryAggregator;
import com.udlive.flinktest.statistics.average.AvgDistanceToWaterAggregator;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Duration;

public class SimpleStatistics {

    static final String telemetryPath = "/Users/serhiiholovko/Downloads/flink_homework/flink_homework/resources/telemetry.dat";


    public static void main(String[] args) throws Exception {
//        System.out.println("Type absolute path to telemetry.dat");
//        Scanner myObj = new Scanner(System.in);
//        String path = myObj.nextLine();

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
                .keyBy(value -> value)
                .windowAll(TumblingProcessingTimeWindows.of(Duration.ofSeconds(1)))
                        .aggregate(new SummaryAggregator());


        summaryStream.print();
        env.execute("Event Count Job");

    }
}

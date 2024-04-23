package com.udlive.flinktest.streaming;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udlive.flinktest.model.Telemetry;
import com.udlive.flinktest.utils.FilePathUtils;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class TelemetryStreamController {

    private final StreamExecutionEnvironment env;

    public TelemetryStreamController() {
        env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.BATCH);
    }

    public TelemetryStreamController(StreamExecutionEnvironment env) {
        this.env = env;
    }

    public DataStream<Telemetry> createParsedStream() {
        final FileSource<String> source =
                FileSource.forRecordStreamFormat(new TextLineInputFormat(), new Path(FilePathUtils.getFilePathFromConsole()))
                        .build();

        DataStreamSource<String> stream =
                env.fromSource(source, WatermarkStrategy.noWatermarks(), "telemetry-file-source");

        SingleOutputStreamOperator<Telemetry> parsedStream = stream.map(jsonString -> {
            ObjectMapper objectMapper =
                    new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(jsonString, Telemetry.class);
        });


        return parsedStream;
    }

    public void startProcessing(String jobName) throws Exception {
        env.execute(jobName);
    }


}

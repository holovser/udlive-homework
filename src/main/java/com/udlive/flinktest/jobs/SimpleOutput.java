package com.udlive.flinktest.jobs;


import com.udlive.flinktest.utils.FilePathUtils;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class SimpleOutput {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        final FileSource<String> source =
                FileSource.forRecordStreamFormat(new TextLineInputFormat(), new Path(FilePathUtils.getFilePathFromConsole()))
                        .build();

        DataStreamSource<String> stream =
                env.fromSource(source, WatermarkStrategy.noWatermarks(), "telemetry-file-source");

        stream.print().name("print-sink");
        env.execute("Simple Output Job");
    }
}

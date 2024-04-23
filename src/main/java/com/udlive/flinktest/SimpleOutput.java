package com.udlive.flinktest;


import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Scanner;

public class SimpleOutput {

    public static void main(String[] args) throws Exception {
//        System.out.println("Type absolute path to telemetry.dat");
//        Scanner myObj = new Scanner(System.in);
//        String path = myObj.nextLine();


        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        final FileSource<String> source =
                FileSource.forRecordStreamFormat(new TextLineInputFormat(), new Path("/Users/serhiiholovko/Downloads/flink_homework/flink_homework/resources/telemetry.dat"))
                        .build();

        DataStreamSource<String> stream =
                env.fromSource(source, WatermarkStrategy.noWatermarks(), "telemetry-file-source");

        stream.print().name("print-sink");
        env.execute("Simple Output Job");
    }
}

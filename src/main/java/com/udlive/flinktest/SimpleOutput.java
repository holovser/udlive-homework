package com.udlive.flinktest;


import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class SimpleOutput {

    public static void main( String[] args ) throws Exception{

        System.out.println("TEST");
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

//        final FileSource<String> source =
//                FileSource.forRecordStreamFormat(new TextLineInputFormat(), new Path("./resources/telemetry.dat"))
//                        .build();
//
//        DataStreamSource<String> text =
//                env.fromSource(source, WatermarkStrategy.noWatermarks(), "telemetry-file-source");


    }
}

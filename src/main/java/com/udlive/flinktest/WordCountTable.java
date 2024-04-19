package com.udlive.flinktest;


import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class WordCountTable{
	
//	private static Logger LOG = LoggerFactory.getLogger(WordCountTable.class);
	private static final String CLASS_NAME = WordCountTable.class.getSimpleName();

//    public static void main( String[] args ) throws Exception{
//
////    	LOG.info(CLASS_NAME + ": starting...");
//        // set up the execution environment
////        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
//    	StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//
//        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
//
//        // get input data
//        DataStream<String> source = env.fromElements(
//                "To be, or not to be,--that is the question:--",
//                "Whether 'tis nobler in the mind to suffer",
//                "The slings and arrows of outrageous fortune",
//                "Or to take arms against a sea of troubles"
//        );
//
//        SingleOutputStreamOperator<String> dataset = source
//	        .flatMap( ( String value, Collector<String> out ) -> {
//		          for( String token : value.toLowerCase().split( "\\W+" ) ){
//		              if( token.length() > 0 ){
//		                  out.collect( token );
//		              }
//		          }
//		      } )
//	        	.returns(String.class);
//
//
//
//        Table tableA = tableEnv.fromDataStream(dataset).as("word");
//        tableEnv.createTemporaryView("InputTable", tableA);
//
//        Table resultTable = tableEnv.sqlQuery( "select word, count(*) from InputTable group by word" );
//
//
//        DataStream<Row> resultStream = tableEnv.toChangelogStream(resultTable);
//
//
//
//        resultStream.print();
//
//        env.execute();
//    }
}
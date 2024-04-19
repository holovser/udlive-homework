package com.udlive.flinktest;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.TupleTypeInfo;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;


public class WordCountStreaming{
	
	

    public static void main( String[] args ) throws Exception{

        // set up the execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    
      
        
        // get input data
        DataStream<String> source = env.fromElements(
                "To be, or not to be,--that is the question:--",
                "Whether 'tis nobler in the mind to suffer",
                "The slings and arrows of outrageous fortune",
                "Or to take arms against a sea of troubles"
        );
        
//        source
//                // split up the lines in pairs (2-tuples) containing: (word,1)
//                .flatMap( new LineSplitter() )
//                // due to type erasure, we need to specify the return type
////                .returns( TupleTypeInfo.getBasicTupleTypeInfo( String.class, Integer.class ) )
//                // group by the tuple field "0"
//                .keyBy( 0 )
//                // sum up tuple on field "1"
//                .sum( 1 );
//        
//        source.print();
        
        DataStream<Tuple2<String, Integer>> counts =
                // The text lines read from the source are split into words
                // using a user-defined function. The tokenizer, implemented below,
                // will output each word as a (2-tuple) containing (word, 1)
                source.flatMap(new Tokenizer())
                        .name("tokenizer")
                        // keyBy groups tuples based on the "0" field, the word.
                        // Using a keyBy allows performing aggregations and other
                        // stateful transformations over data on a per-key basis.
                        // This is similar to a GROUP BY clause in a SQL query.
                        .keyBy(value -> value.f0)
                        // For each key, we perform a simple sum of the "1" field, the count.
                        // If the input data stream is bounded, sum will output a final count for
                        // each word. If it is unbounded, it will continuously output updates
                        // each time it sees a new instance of each word in the stream.
                        .sum(1)
                        .name("counter");
                
        counts.print().name("print-sink");
        // start the job
        env.execute("WordCountStreaming");
    }
    
    public static final class Tokenizer
	    implements FlatMapFunction<String, Tuple2<String, Integer>> {
	
		@Override
		public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
		    // normalize and split the line
		    String[] tokens = value.toLowerCase().split("\\W+");
		
		    // emit the pairs
		    for (String token : tokens) {
		        if (token.length() > 0) {
		            out.collect(new Tuple2<>(token, 1));
		        }
		    }
		}
	}
}
 
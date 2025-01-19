package org.epf.hadoop.colfil2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class RelationshipReducer extends Reducer<UserPair, IntWritable, UserPair, IntWritable> {
    private final IntWritable result = new IntWritable();

    @Override
    protected void reduce(UserPair key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        boolean isDirectRelation = false;

        for (IntWritable value : values) {
            if (value.get() == 0) {
                isDirectRelation = true;
            } else {
                sum += value.get();
            }
        }

        if (!isDirectRelation && sum > 0) {
            result.set(sum);
            context.write(key, result);
        }
    }
}
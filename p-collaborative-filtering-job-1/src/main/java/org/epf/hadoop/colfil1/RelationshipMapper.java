package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class RelationshipMapper extends Mapper<LongWritable, Relationship, Text, Text> {
    private Text userKey = new Text();
    private Text friendValue = new Text();

    @Override
    protected void map(LongWritable key, Relationship value, Context context) throws IOException, InterruptedException {
        // Relation unidirectionnelle : A -> B
        userKey.set(value.getId1());
        friendValue.set(value.getId2());
        context.write(userKey, friendValue);

        // Relation unidirectionnelle inversée : B -> A
        userKey.set(value.getId2());
        friendValue.set(value.getId1());
        context.write(userKey, friendValue);
    }
}
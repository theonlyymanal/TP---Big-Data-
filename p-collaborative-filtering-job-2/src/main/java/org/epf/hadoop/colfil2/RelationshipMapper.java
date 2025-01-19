package org.epf.hadoop.colfil2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class RelationshipMapper extends Mapper<Object, Text, UserPair, IntWritable> {
    private final UserPair userPair = new UserPair();
    private final IntWritable one = new IntWritable(1);
    private final IntWritable zero = new IntWritable(0);

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] tokens = value.toString().split("\t");
        if (tokens.length != 2) return;

        String user = tokens[0];
        String[] friends = tokens[1].split(",");

        ArrayList<String> friendList = new ArrayList<>();
        Collections.addAll(friendList, friends);


        for (int i = 0; i < friendList.size(); i++) {
            for (int j = i + 1; j < friendList.size(); j++) {
                userPair.set(friendList.get(i), friendList.get(j));
                context.write(userPair, one);
            }
        }

        for (String friend : friendList) {
            userPair.set(user, friend);
            context.write(userPair, zero);
        }
    }
}
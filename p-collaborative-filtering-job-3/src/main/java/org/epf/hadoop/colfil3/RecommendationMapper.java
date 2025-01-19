package org.epf.hadoop.colfil3;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class RecommendationMapper extends Mapper<Object, Text, Text, Text> {
    private Text user = new Text();
    private Text recommendation = new Text();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // Ligne d'entrée : "1,8\t3"
        String[] line = value.toString().split("\t");
        if (line.length != 2) return;

        String[] users = line[0].split(",");
        String count = line[1];

        if (users.length == 2) {
            // j'émis deux paires : (1 -> 8:3) et (8 -> 1:3)
            user.set(users[0]);
            recommendation.set(users[1] + ":" + count);
            context.write(user, recommendation);

            user.set(users[1]);
            recommendation.set(users[0] + ":" + count);
            context.write(user, recommendation);
        }
    }
}
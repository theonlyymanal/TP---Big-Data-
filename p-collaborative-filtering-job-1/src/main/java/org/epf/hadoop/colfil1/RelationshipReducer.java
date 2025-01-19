package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class RelationshipReducer extends Reducer<Text, Text, Text, Text> {
    private Text friendsList = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // Utilisez un Set pour éviter les doublons
        Set<String> uniqueFriends = new HashSet<>();
        for (Text value : values) {
            uniqueFriends.add(value.toString());
        }

        // Crée une liste d'amis séparée par des virgules
        StringBuilder friends = new StringBuilder();
        for (String friend : uniqueFriends) {
            if (friends.length() > 0) {
                friends.append(",");
            }
            friends.append(friend);
        }

        // Définit la liste comme valeur
        friendsList.set(friends.toString());
        context.write(key, friendsList);
    }
}
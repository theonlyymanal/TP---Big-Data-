package org.epf.hadoop.colfil3;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecommendationReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // Je récupér toutes les recommandations pour un utilisateur
        List<String> recommendations = new ArrayList<>();

        for (Text val : values) {
            recommendations.add(val.toString());
        }

        // Je tris les recommandations par ordre décroissant du nombre de relations communes
        Collections.sort(recommendations, (a, b) -> {
            int countA = Integer.parseInt(a.split(":")[1]);
            int countB = Integer.parseInt(b.split(":")[1]);
            return Integer.compare(countB, countA); // Ordre décroissant
        });

        // Je garde uniquement les 5 premières recommandations
        List<String> topRecommendations = recommendations.subList(0, Math.min(5, recommendations.size()));

        // j'écris les recommandations triées
        context.write(key, new Text(String.join(", ", topRecommendations)));
    }
}
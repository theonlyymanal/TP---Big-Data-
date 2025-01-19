package org.epf.hadoop.colfil1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ColFilJob1 {
    public static void main(String[] args) throws Exception {
        // Vérification des arguments
        if (args.length != 2) {
            System.err.println("Usage: ColFilJob1 <input path> <output path>");
            System.exit(-1);
        }

        // Configuration Hadoop
        Configuration conf = new Configuration();

        // Création du job
        Job job = Job.getInstance(conf, "Collaborative Filtering Job 1");
        job.setJarByClass(ColFilJob1.class);

        // Configuration des classes Mapper et Reducer
        job.setMapperClass(RelationshipMapper.class);
        job.setReducerClass(RelationshipReducer.class);

        // Configuration des types de sortie du Mapper
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        // Configuration des types de sortie du Reducer
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // Utilisation de l'InputFormat personnalisé
        job.setInputFormatClass(RelationshipInputFormat.class);

        // Configuration des chemins d'entrée et de sortie
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // Définir le nombre de reducers
        job.setNumReduceTasks(2); // Suivant le sujet

        // Exécution du job
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

import java.io.IOException;

public class RelationshipRecordReader extends RecordReader<LongWritable, Relationship> {
    private LineRecordReader lineRecordReader = new LineRecordReader();
    private LongWritable currentKey = new LongWritable();
    private Relationship currentValue = new Relationship();

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        lineRecordReader.initialize(split, context);
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        // Vérifie s'il reste des lignes à lire dans l'InputSplit
        boolean hasNext = lineRecordReader.nextKeyValue();
        if (hasNext) {
            // Récupère la clé (numéro de ligne)
            currentKey.set(lineRecordReader.getCurrentKey().get());

            // Récupère la valeur (contenu de la ligne) depuis LineRecordReader
            String line = lineRecordReader.getCurrentValue().toString();

            // Analyse la ligne pour créer un objet Relationship
            // Format attendu : "A<->B;timestamp"
            String[] parts = line.split("<->|;");
            if (parts.length >= 2) {
                currentValue.setId1(parts[0].trim());
                currentValue.setId2(parts[1].trim());
            } else {
                throw new IOException("Invalid line format: " + line);
            }
        }
        return hasNext;
    }

    @Override
    public LongWritable getCurrentKey() throws IOException, InterruptedException {
        return currentKey;
    }

    @Override
    public Relationship getCurrentValue() throws IOException, InterruptedException {
        return currentValue;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return lineRecordReader.getProgress();
    }

    @Override
    public void close() throws IOException {
        lineRecordReader.close();
    }
}
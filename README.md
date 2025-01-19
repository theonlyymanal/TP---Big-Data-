# TP - Big Data - Collaborative Filtering

This repository contains the code for a *Collaborative Filtering* project using Hadoop MapReduce. The goal of the project is to process a large dataset to provide relationship recommendations for users based on common connections in a social network.

## Table of Contents

- [Description](#description)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [How to Run the Jobs](#how-to-run-the-jobs)
- [Commands Used](#commands-used)
- [Output](#output)

## Description

In this project, we use *Hadoop MapReduce* to:
1. Process raw data (relationships between users) and create a *job1*.
2. Find common relationships between users to suggest new relationships (*job2*).
3. Provide *relationship recommendations* for users based on job2 results (*job3*).

The project demonstrates the use of Hadoop's distributed processing capabilities to handle large datasets and run parallel jobs for collaborative filtering in a social network context.

## Technologies Used

- *Hadoop* - Framework for distributed data processing.
- *MapReduce* - Data processing model used to perform computations across a cluster.
- *Docker* - Containerization used to package the environment and run Hadoop jobs.
- *Git* - Version control system to manage project code.

## Setup Instructions

1. *Clone this repository* to your local machine:
git clone https://github.com/theonlyymanal/TP—Big-Data-.git

2. *Set up Docker*:
- Ensure Docker is installed and running.
- Build the Docker image:
  
  docker build -t big-data-img ./deploy
  

3. *Run the Docker container*:
docker run -it –rm -p 8088:8088 -p 9870:9870 -p 9864:9864 -v “$(pwd)/data:/data” -v “$(pwd)/jars:/jars” –name big-data-container big-data-img

4. *Access the Docker container's bash shell*:
 docker exec -it big-data-container bash

## How to Run the Jobs

### Job 1: Process Raw Data
- Upload the data to HDFS:
hdfs dfs -put /data/relationships/data.txt /data/
- Run job1:
hadoop jar jars/tpfinal-manal-chigueur-job1.jar /data/data.txt /output/job1/
- Check the output:
hdfs dfs -cat /output/job1/part-r-*

### Job 2: Find Common Relationships
- Run job2:
hadoop jar jars/tpfinal-manal-chigueur-job2.jar /output/job1/ /output/job2/
- Check the output:
hdfs dfs -cat /output/job2/part-r-*

### Job 3: Provide Relationship Recommendations
- Run job3:
hadoop jar jars/tpfinal-manal-chigueur-job3.jar /output/job2/ /output/job3/
- Check the output:
hdfs dfs -cat /output/job3/part-r-*

## Commands Used

Here are the main commands used during the project:

1. *Building the Docker Image*:
docker build -t big-data-img ./deploy
2. *Running the Docker Container*:
docker run -it -rm -p 8088:8088 -p
9870:9870 -p 9864:9864 -v "$(pwd)/data:/ data" -v "$(pwd)/jars:/jars" —name big-data-container big-data-img

3. *HDFS Commands*:
- Uploading files to HDFS:
  
  hdfs dfs -put /data/relationships/data.txt /data/
  
- Viewing HDFS output:
  
  hdfs dfs -cat /output/job1/part-r-*
  

4. *Running MapReduce Jobs*:
- Running job1:
  
  hadoop jar jars/tpfinal-manal-chigueur-job1.jar /data/data.txt /output/job1/
  
- Running job2:
  
  hadoop jar jars/tpfinal-manal-chigueur-job2.jar /output/job1/ /output/job2/
  
- Running job3:
  
  hadoop jar jars/tpfinal-manal-chigueur-job3.jar /output/job2/ /output/job3/
  

## Output

- After running all three jobs, the final output is stored in /output/job3/, which contains the relationship recommendations for users based on common connections.

Example:
User A: [User B, User C, …]
Each user will have a list of recommended users to connect with, sorted by the number of common relationships.


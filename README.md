# UDlive Homework Project

## Description
This project is a simple Java program which can be built with maven and executed from IDE or running a jar file.
## Build and Run
I highly recommend using Amazon Corretto jdk to buid and run this program as I experienced some issues which deserialisation and Jackson library and running with different JDK might require to change the code or update dependencies.
You can simply run the jar files provided with a jdk, each files represents a Flink job. Unfortunately JAR files 
got too big to be stored on github so I'm attaching a link to Google Drive: 
[Google Drive](https://drive.google.com/drive/folders/19X5XXcnd9xZbVjrxi5DCjbznonERihdC?usp=sharing)
It should be also extremely easy to run any job from Intellij IDE.

## Challenges encountered
The most challenging task was implementing a Daily Summary job. I was trying to adjust my code to use watermarking and windowing but for some reason watermarks were not correct 
and I couldn't split a stream so that each window contains events from a particular day.

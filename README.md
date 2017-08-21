# Project Title

WebSphere MQ library sink and source for Spring Cloud Data Flow

## Getting Started

There are three projects that complete this project.  One project wmq-library is reused by the sink and source and is using
Spring Auto Configuration to bootstrap the sink and source projects.  

To build the core sink and source Spring Boot projects:

gradlew build

### Prerequisites

The WebSphere MQ libraries must be placed in the lib directory in the root of the project.  These are non-shareable files from IBM, so they
are excluded from this repository.    

This project currently uses the Kafka binder.  

### Installing

Kafka will need to be running in order to support the Spring Cloud Data Flow binder.     

Below are examples for installing the sink and source as part of Spring Cloud Data Flow. These can be ran in the Spring Cloud Data Flow shell.

#### WebSphere MQ Source

Register your stream here:

```
app register --name wmq --type source --uri file:///YOUR_PATH_TO_JAR/wmq-source-0.0.1-SNAPSHOT.jar
```

An example source (i.e. that is reading from WebSphere MQ) stream would be created this way:

```
stream create --definition "wmq | log" --name mqlog
```

To deploy the stream, run something like the following depending on the stream name and actual Kafka binder addresses:

```
stream deploy mqlog --properties "app.wmq.spring.cloud.stream.kafka.binder.brokers=192.168.99.100:9092,app.wmq.spring.cloud.stream.kafka.binder.zkNodes=192.168.99.100:2181,app.log.spring.cloud.stream.kafka.binder.brokers=192.168.99.100:9092,app.log.spring.cloud.stream.kafka.binder.zkNodes=192.168.99.100:2181"
```

#### WebSphere MQ Sink

Register your stream here:

```
app register --name wmq --type sink --uri file:///YOUR_PATH_TO_JAR/wmq-sink-0.0.1-SNAPSHOT.jar
```

An example sink (i.e. that is writing to WebSphere MQ) stream would be created this way:

```
stream create --definition "time | wmq" --name mqtime
```

To deploy the stream, run something like the following depending on the stream name and actual Kafka binder addresses:

```
stream deploy mqtime --properties "app.wmq.spring.cloud.stream.kafka.binder.brokers=192.168.99.100:9092,app.wmq.spring.cloud.stream.kafka.binder.zkNodes=192.168.99.100:2181,app.time.spring.cloud.stream.kafka.binder.brokers=192.168.99.100:9092,app.time.spring.cloud.stream.kafka.binder.zkNodes=192.168.99.100:2181"
```

## License

This project is licensed under the Apache License - see the [LICENSE](LICENSE) file for details


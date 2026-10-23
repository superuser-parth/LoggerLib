# Java Logger Library

A configurable logging library built in Java, supporting multiple log levels, configurable output sinks, timestamp formatting, and automatic log file rotation with GZIP compression.

## Prerequisites

- JDK 17 or newer
- Gradle Wrapper (`gradlew`)

## Build and Run

Build the project:

./gradlew clean build


Run the application:

./gradlew run

The application creates a `logs/` directory relative to its working directory and writes log messages to `application.log`.

## Usage

### 1. Create a formatter

```java
LogFormatter formatter =
        new LogFormatter("yyyy:MM:dd HH:mm:ss");
```

### 2. Configure the file sink

```java
FileSinkConfig fileConfig =
        new FileSinkConfig(1024 * 1024, 3);

Sink fileSink = new FileSink(fileConfig, formatter);
```

The example sets a maximum active file size of 1 MiB and retains up to three compressed backups.

### 3. Configure log destinations

```java
Map<LogLevel, Sink> sinks =
        new EnumMap<>(LogLevel.class);

sinks.put(LogLevel.DEBUG, fileSink);
sinks.put(LogLevel.INFO, fileSink);
sinks.put(LogLevel.WARN, fileSink);
sinks.put(LogLevel.ERROR, fileSink);
sinks.put(LogLevel.FATAL, fileSink);
```

Each log level is mapped to its destination. Multiple levels can share the same sink. If a level has no configured sink, its messages are ignored.

### 4. Create the logger

```java
LoggerConfig config =
        new LoggerConfig("yyyy-MM-dd HH:mm:ss", sinks);

Logger logger = new Logger(config);
```

### 5. Log messages

```java
logger.debug("PaymentService", "Payment request received");
logger.info("PaymentService", "Payment initiated");
logger.warn("PaymentService", "Payment is taking longer than expected");
logger.error("PaymentService", "Payment processing failed");
logger.fatal("Application", "Critical application failure");
```


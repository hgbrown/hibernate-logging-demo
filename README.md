![build status](https://travis-ci.org/hgbrown/hibernate-logging-demo.svg?branch=master)

# Hibernate Logging Demo

One of the ways you can use to identify performance problems when in development is the use of logging.
This project demonstrates how to setup logging in Hibernate and Spring Boot to be able to detect possible performance problems during development

The first thing to to is to active the `hibernate.generate_statistics` property via a system property.
In this demo, this is done by setting this property in the Gradle build when the test task is run.

We then have to configure the logging to display the metrics by setting the logger: `org.hibernate.stat` to `debug`

Doing this should result in a log line as follows being visible:

```
2019-08-08 16:46:21.804 DEBUG 2553 --- [    Test worker] o.h.stat.internal.StatisticsInitiator    : Statistics initialized [enabled=true]
``` 

At the end of the session, there should be summary as follows in the log:

```
2019-08-08 16:46:22.030  INFO 2553 --- [    Test worker] i.StatisticalLoggingSessionEventListener : Session Metrics {
    163771 nanoseconds spent acquiring 1 JDBC connections;
    0 nanoseconds spent releasing 0 JDBC connections;
    4791166 nanoseconds spent preparing 12 JDBC statements;
    7518216 nanoseconds spent executing 12 JDBC statements;
    0 nanoseconds spent executing 0 JDBC batches;
    0 nanoseconds spent performing 0 L2C puts;
    0 nanoseconds spent performing 0 L2C hits;
    0 nanoseconds spent performing 0 L2C misses;
    0 nanoseconds spent executing 0 flushes (flushing a total of 0 entities and 0 collections);
    39226 nanoseconds spent executing 1 partial-flushes (flushing a total of 0 entities and 0 collections)
}
```

From the above, you can see how many rows were returned as well as how long the queries took to execute.
You should always check that the number of statements executed matches your expectation since this could show inefficient implementations.


[Session Metrics Example](https://github.com/hgbrown/hibernate-logging-demo/blob/master/docs/session-metrics.png)

## More logging configuration worth noting

### Ensure show_sql property is false

```properties
spring.jpa.show-sql=false
```

THis uses standard system out so cannot be controlled by changing logging levels

### Logging SQL Statements and their bind parameters

```properties

# SQL Statements
logging.level.org.hibernate.SQL=debug

# SQL parameters
logging.level.org.hibernate.type.descriptor.sql=trace
```

### Log 2nd Level Cache if enabled

```properties
# 2nd Level Cache
logging.level.org.hibernate.cache=debug
```

### Log DDL statements if enabled

```properties
# DDL SQL statements
logging.level.org.hibernate.tool.hbm2ddl=debug
```

### Ensure logging level set to error only for production

```properties
logging.level.org.hibernate=error
```

Do not set to `WARN` in production since this will result in Hibernate executing additional JDBC statements to check if the database returned any warnings.
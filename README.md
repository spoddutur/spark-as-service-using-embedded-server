# spark-as-service-using-embedded-server
This application comes as Spark2.1-REST-Service-Provider using an embedded, Reactive-Streams-based, fully asynchronous HTTP server

## 1. Central Idea

ApacheSpark is an in-memory big-data cluster computing framework. I wanted to build an interactive REST api service on top of my ApacheSpark application which serves use-cases like:
Load the trained model in SparkSession and quickly do the prediction for user given query. 
Have your big-data cached in cluster and provide user an endpoint to query it.
Run some recurrent spark queries with varying parameters

As you can see that the ```core``` of the application is not primarily a web-application OR browser-interaction but to have REST service performing big-data cluster-computation on ApacheSpark.

## 2. Akka-HTTP as embedded server:
I found Akka-HTTP to be right fit for the usecases mentioned above.
**Reason**: 

With Akka-Http, you normally don’t build your application ```on top of``` Akka HTTP, but you build your application on top of whatever makes sense and use Akka HTTP merely for the HTTP integration needs. Following picture depicts Akka-Http architecture and hopefully, the project title ```spark-as-service-using-embedded-server``` will make sense now.
![image](https://user-images.githubusercontent.com/22542670/27826638-65c407ea-60d1-11e7-9526-ae68de34ea30.png)

## 3. Architecture
Following picture illustrates the workflow of the application

![image](https://user-images.githubusercontent.com/22542670/27823530-0b770dc8-60c7-11e7-9b22-c304fe3327fb.png)

## 4. Building
It uses [Scala 2.11](#scala), [Spark 2.1](#spark) and [Akka-Http](#akka-http)
```markdown
mvn clean install
```

## 5. Execution
We can start our application as stand-alone jar like this:
```markdown
mvn exec:java
```

### 5.1 Now, you can access following 4 routes:
1. **homepage** - http://localhost:8001 - says "hello world"
2. **version** - http://localhost:8001/version - queries shared SparkSession and tells "spark version"
3. **activeStreams** - http://localhost:8001/activeStreams - tells how many spark streams are active currently
4. count - http://localhost:8001/count - random spark job to count number of elements in a sequence.

### 5.2 cmd-line-args

Optionally, you can provide configuration params like spark-master, akka-port etc from command line. To see the list of configurable params, just type:

```markdown
mvn exec:java -Dexec.args="--help" 
OR 
mvn exec:java -Dexec.args=“-h"
```

Help content will look something like this:
This application comes as Spark2.1-REST-Service-Provider using an embedded,
Reactive-Streams-based, fully asynchronous HTTP server (i.e., using akka-http).
So, this application needs config params like AkkaWebPort to bind to, SparkMaster
and SparkAppName

Usage: spark-submit spark-as-service-using-embedded-server.jar [options]
  Options:
  -h, --help
  -m, --master <master_url>                    spark://host:port, mesos://host:port, yarn, or local. Default: $sparkMasterDef
  -n, --name <name>                            A name of your application. Default: $sparkAppNameDef
  -p, --akkaHttpPort <portnumber>              Port where akka-http is binded. Default: $akkaHttpPortDef

Configured 4 routes:
1. homepage - http://host:port - says "hello world"
2. version - http://host:port/version - tells "spark version"
3. activeStreams - http://host:port/activeStreams - tells how many spark streams are active currently
4. count - http://host:port/count - random spark job to count a seq of integers
```

## 6. References
[Akka](http://doc.akka.io/docs/akka-http/current/scala/http/introduction.html)

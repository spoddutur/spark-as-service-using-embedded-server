# spark-as-service-using-embedded-server
This application comes as Spark2.1-REST-Service-Provider using an embedded, Reactive-Streams-based, fully asynchronous HTTP server.

## 1. Central Idea
I wanted to build an interactive REST api service on top of my ApacheSpark application which serves use-cases like:
```ini
- Load the trained model in SparkSession and quickly do the prediction for user given query._
- Have your big-data cached in cluster and provide user an endpoint to query it.
- Run some recurrent spark queries with varying parameters.
```

As you can see that the ```core``` of the application is not primarily a web-application OR browser-interaction but to have REST service performing big-data cluster-computation on ApacheSpark.

## 2. Akka-HTTP as apt-fit:
With Akka-Http, you normally don’t build your application ```on top of``` Akka HTTP, but you build your application on top of whatever makes sense and use Akka HTTP merely for the HTTP integration needs. So, I found Akka-HTTP to be right fit for the usecases mentioned above.

## 3. Architecture
### 3.1 To demo this, I've configured following four routes:
1. **homepage** - [http://localhost:8001](#homepage) - says "hello world"
2. **version** - [http://localhost:8001/version](#version) - queries shared SparkSession and tells "spark version"
3. **activeStreams** - [http://localhost:8001/activeStreams](#activeStreams) - tells how many spark streams are active currently
4. **count** - [http://localhost:8001/count](#count) - random spark job to count number of elements in a sequence.

Following picture illustrates the routing of a HttpRequest:
<img src="https://user-images.githubusercontent.com/22542670/27865894-ee70d42a-61b1-11e7-9595-02b845a9ffae.png" width="700"/>

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
### 5.1 cmd-line-args
Optionally, you can provide configuration params like spark-master, akka-port etc from command line. To see the list of configurable params, just type:
```markdown
mvn exec:java -Dexec.args="--help" 
OR 
mvn exec:java -Dexec.args=“-h"
```

```ini
Help content will look something like this:
This application comes as Spark2.1-REST-Service-Provider using an embedded,
Reactive-Streams-based, fully asynchronous HTTP server (i.e., using akka-http).
So, this application needs config params like AkkaWebPort to bind to, SparkMaster
and SparkAppName

Usage: spark-submit spark-as-service-using-embedded-server.jar [options]
  Options:
  -h, --help
  -m, --master <master_url>                    spark://host:port, mesos://host:port, yarn, or local. Default: local
  -n, --name <name>                            A name of your application. Default: SparkAsRestService
  -p, --akkaHttpPort <portnumber>              Port where akka-http is binded. Default: 8001
```
### 5.2 Tweak Default cmd-line args
There are 2 ways to change the default param values:
1. Update ```src/main/resources/application.conf``` file directly. Build and then Run
2. ```mvn exec:java -Dexec.args="--master <master> --name <spark-app-name> --akkaHttpPort <port-to-which-akka-should-listen-to>"```

## 6. References
[Akka](http://doc.akka.io/docs/akka-http/current/scala/http/introduction.html)

package com.spoddutur.web

/**
  * Created by sruthi on 03/07/17.
  */
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{HttpApp, Route}
import com.spoddutur.spark.SparkFactory

/**
  * Http Server definition
  * Configured 4 routes:
  * 1. homepage - http://host:port - says "hello world"
  * 2. version - http://host:port/version - tells "spark version"
  * 3. activeStreams - http://host:port/activeStreams - tells how many spark streams are active currently
  * 4. count - http://host:port/count - random spark job to count a seq of integers
   */
object WebServer extends HttpApp {
  case class Colour(r: Int, g:Int, b:Int) {
    require(r >=0 && r<=255, "Wrong color pallete")
    require(g >=0 && g<=255, "Wrong color pallete")
    require(b >=0 && b<=255, "Wrong color pallete")
  }

  override def routes: Route = {
    pathEndOrSingleSlash {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1>Hello World!! This is Akka responding..</h1>"))
      }
    } ~
      path("version") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1>Spark version: ${SparkFactory.sc.version}</h1>"))
        }
      } ~
      path("activeStreams") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1>Current active streams in SparkContext: ${HttpService.activeStreamsInSparkContext()}"))
        }
      } ~
      path("count") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1>Count 0 to 500000 using Spark with 25 partitions: ${HttpService.count()}"))
        }
      } ~
    path("customer"/IntNumber) { id =>
        complete {
          s"CustId: ${id}"
        }
    } ~
    path("customer") {
      parameter('id.as[Int]) { id =>
        complete {
          s"CustId: ${id}"
        }
      }
    } ~
    path("color") {
      parameters('r.as[Int], 'g.as[Int], 'b.as[Int]) { (r1, g, b) =>

        complete {
         s"(R,G,B): ${r1}, ${g}, ${b}"
        }
      }
    }
}}
import sbt.Keys._
import play.sbt.PlaySettings

name := """play-first"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val scala213 = "2.13.12"

lazy val root = (project in file("."))
                    .enablePlugins(PlayScala, PlayService, PlayLayoutPlugin)
                    .settings(
                        name := "play-scala-rest-api-example",
                        scalaVersion := scala213,
                        libraryDependencies ++= Seq(
                          guice,
                          "org.joda" % "joda-convert" % "2.2.3",
                          "net.logstash.logback" % "logstash-logback-encoder" % "7.3",
                          "io.lemonlabs" %% "scala-uri" % "4.0.3",
                          "net.codingwell" %% "scala-guice" % "6.0.0",
                          "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test
                        ),
                        scalacOptions ++= Seq(
                          "-feature",
                          "-Werror"
                        )
                      )
                    

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"

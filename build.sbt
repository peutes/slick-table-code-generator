import sbt._

lazy val root = (project in file("."))
  .settings(
    inThisBuild(List(
      scalaVersion := "2.12.4",
      version := "1.0.3"
    )),
    name := "SlickTableCodeGenerator",
    libraryDependencies ++= List(
      "com.typesafe.slick" %% "slick" % "3.2.0",
      "com.typesafe.slick" %% "slick-codegen" % "3.2.0",
      "joda-time" % "joda-time" % "2.7",
      "com.github.tototoshi" %% "slick-joda-mapper" % "2.3.0",
      "org.scalatest" %% "scalatest" % "3.0.3" % Test,
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "mysql" % "mysql-connector-java" % "6.0.5",
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.joda" % "joda-convert" % "1.7",
      "com.typesafe" % "config" % "1.3.1"
    ),
    dependencyClasspath in Runtime += baseDirectory.value / "config"
  )

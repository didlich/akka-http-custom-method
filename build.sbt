import Dependencies._

//val http4sVersion = "0.15.3"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "method",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "scala-app",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "com.typesafe.akka" %% "akka-actor" % "2.4.17",
      "com.typesafe.akka" %% "akka-http-core" % "10.0.4",
      "com.typesafe.akka" %% "akka-http" % "10.0.4"
    )

  )

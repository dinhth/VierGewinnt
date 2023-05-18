val scala3Version = "3.2.2"

val akkaVersion = "2.8.0"
val akkaHttp = "10.5.0"

lazy val commonSettings = Seq(
  version := "0.2.0-SNAPSHOT",
  scalaVersion := scala3Version,
  libraryDependencies ++= Seq(
    "org.scalactic" %% "scalactic" % "3.2.15",
    "org.scalatest" %% "scalatest" % "3.2.15" % "test",
    "com.google.inject" % "guice" % "5.1.0",
    ("net.codingwell" %% "scala-guice" % "5.1.1").cross(CrossVersion.for3Use2_13),
    "org.scala-lang.modules" %% "scala-xml" % "2.1.0",
    "com.typesafe.play" %% "play-json" % "2.10.0-RC7",
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j"      % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-http" % akkaHttp,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttp,
    "org.slf4j" % "slf4j-nop" % "2.0.5",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
    "com.typesafe.akka" %% "akka-http-core"  % akkaHttp,
    "ch.qos.logback"    %  "logback-classic" % "1.4.6",
    "com.typesafe.akka" %% "akka-testkit"    % akkaVersion   % "test"
  )
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "persistence",
    description := "Persistence for Vier Gewinnt",
    commonSettings
  )

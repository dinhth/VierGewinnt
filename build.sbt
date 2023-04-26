val scala3Version = "3.2.2"

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
    "com.typesafe.akka" %% "akka-http" % "10.5.0",
    "com.typesafe.akka" %% "akka-actor" % "2.8.0",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.0",
    "com.typesafe.akka" %% "akka-actor-typed" % "2.8.0",
    "com.typesafe.akka" %% "akka-stream" % "2.8.0",
    "org.slf4j" % "slf4j-nop" % "2.0.5"
  ),
  coverageEnabled := true
)

lazy val util = project
  .in(file("util"))
  .settings(name := "util", description := "Util for Vier Gewinnt", commonSettings)

lazy val gui = project
  .in(file("gui"))
  .settings(
    name := "gui",
    description := "GUI for Vier Gewinnt",
    commonSettings,
    libraryDependencies += "org.scalafx" %% "scalafx" % "20.0.0-R31"
  )
  .dependsOn(core)

lazy val tui = project
  .in(file("tui"))
  .settings(name := "tui", description := "TUI for Vier Gewinnt", commonSettings)
  .dependsOn(core)

lazy val core = project
  .in(file("core"))
  .settings(
    name := "core",
    description := "Core for Vier Gewinnt",
    commonSettings
  )
  .dependsOn(model, util) //persistence

lazy val persistence = project
  .in(file("persistence"))
  .settings(
    name := "persistence",
    description := "Persistence for Vier Gewinnt",
    commonSettings
  )
  .dependsOn(model)

lazy val model = project
  .in(file("model"))
  .settings(
    name := "model",
    description := "Model for Vier Gewinnt",
    commonSettings
  )

lazy val root = project
  .in(file("."))
  .settings(
    name := "VierGewinnt",
    commonSettings
  )
  .aggregate(gui, tui, core, util, model, persistence)

val scala3Version = "3.0.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "VierGewinnt",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"
  )
  .enablePlugins(JacocoCoverallsPlugin)

  jacocoExcludes := Seq("src/main/scala/de/htwg/se/VierGewinnt/VierGewinnt.scala")

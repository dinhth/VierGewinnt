val scala3Version = "3.0.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "VierGewinnt",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    crossScalaVersions ++= Seq("2.13.5", "3.0.2"),
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test",
    libraryDependencies += ("org.scalafx" %% "scalafx" % "17.0.1-R26"),
    libraryDependencies += ("com.google.inject" % "guice"% "5.0.1"),
    libraryDependencies += ("net.codingwell" %% "scala-guice" % "5.0.1").cross(CrossVersion.for3Use2_13),
    jacocoCoverallsServiceName := "github-actions",
    jacocoCoverallsBranch := sys.env.get("CI_BRANCH"),
    jacocoCoverallsPullRequest := sys.env.get("GITHUB_EVENT_NAME"),
    jacocoCoverallsRepoToken := sys.env.get("COVERALLS_REPO_TOKEN"),
    jacocoExcludes in Test := Seq(
      "de.htwg.se.VierGewinnt.VierGewinnt.run*",
      "de.htwg.se.VierGewinnt.aview.GUI*",
      "de.htwg.se.VierGewinnt.model.enemyStrategyMockImpl.EnemyStrategy*",
      "de.htwg.se.VierGewinnt.gridComponent.gridMockImpl.Grid*",
      "de.htwg.se.VierGewinnt.playerComponent.playerMockImpl.Player*"
    ),
    libraryDependencies ++= {
      // Determine OS version of JavaFX binaries
      lazy val osName = System.getProperty("os.name") match {
        case n if n.startsWith("Linux") => "linux"
        case n if n.startsWith("Mac") => "mac"
        case n if n.startsWith("Windows") => "win"
        case _ => throw new Exception("Unknown platform!")
      }

      Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
        .map(m => "org.openjfx" % s"javafx-$m" % "17.0.1" classifier osName)
    }
  )
  .enablePlugins(JacocoCoverallsPlugin)

import sbt._

object MyBuild extends Build {
    lazy val root = Project("root", file(".")) dependsOn(hipchatScalaProject)
    lazy val hipchatScalaProject = RootProject(uri("https://github.com/poweld/hipchat-scala.git"))
}

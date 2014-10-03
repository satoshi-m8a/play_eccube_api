name := """play_eccube_api"""

version := "1.0-SNAPSHOT"

lazy val api = project.in(file("modules/api")).enablePlugins(PlayScala)
lazy val search = project.in(file("modules/search")).enablePlugins(PlayScala)

lazy val root =
  project.in(file("."))
    .enablePlugins(PlayScala).enablePlugins(SbtWeb)
    .aggregate(api, search)
    .dependsOn(api, search)


scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.postgresql" % "postgresql" % "9.3-1102-jdbc41",
  "org.webjars" % "jquery" % "2.1.1",
  "org.webjars" % "bootstrap" % "3.2.0",
  "org.webjars" % "angularjs" % "1.3.0-rc.1"
)


includeFilter in(Assets, LessKeys.less) := "*.less"

excludeFilter in(Assets, LessKeys.less) := "_*.less"

pipelineStages := Seq(rjs, digest, gzip)

RjsKeys.modules := Seq(
  WebJs.JS.Object("name" -> "main"),
  WebJs.JS.Object("name" -> "main_admin")
)


scalacOptions in ThisBuild ++= Seq(
  "-encoding", "UTF-8",
  "-deprecation", // warning and location for usages of deprecated APIs
  "-feature", // warning and location for usages of features that should be imported explicitly
  "-unchecked", // additional warnings where generated code depends on assumptions
  "-Xlint", // recommended additional warnings
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
  "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
  "-Ywarn-inaccessible",
  "-Ywarn-dead-code"
)

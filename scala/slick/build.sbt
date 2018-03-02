import Dependencies._
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.2.4")
resolvers += Resolver.sonatypeRepo("releases")

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.xivvic",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "slick",
    libraryDependencies += scalaTest % Test
  )


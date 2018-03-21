name := """learn-programming"""
organization := "gsdev9"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.4"

libraryDependencies += guice

libraryDependencies ++= Seq(
  javaJdbc,
  javaWs,
  javaJpa,
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "org.hibernate" % "hibernate-entitymanager" % "4.3.8.Final", // replace by your jpa implementation
  "mysql" % "mysql-connector-java" % "5.1.45",
  "org.pac4j" % "play-pac4j" % "3.0.0",
  "org.pac4j" % "pac4j-oauth" % "2.0.0"
)
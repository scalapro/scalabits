
name := "circle1"

version := "1.0"

scalaVersion := "2.10.2"

// scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

libraryDependencies ++= Seq(
    "com.typesafe.slick" %% "slick" % "1.0.0",
    "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test",
    "com.typesafe" % "config" % "1.0.2",
    "org.slf4j" % "slf4j-api" % "1.7.2",
    "com.h2database" % "h2" % "1.3.166",
    "ch.qos.logback" % "logback-core" % "1.0.7",
    "ch.qos.logback" % "logback-classic" % "1.0.7",
    "org.scalaz" %% "scalaz-core" % "7.0.3",
    "org.scalaz" %% "scalaz-effect" % "7.0.3",
    "org.scalaz" %% "scalaz-typelevel" % "7.0.3",
    "org.scalaz" %% "scalaz-scalacheck-binding" % "7.0.3" % "test"
)

//    "org.slf4j" % "slf4j-nop" % "1.6.4",

EclipseKeys.withSource := true

resolvers += "Sonatype OSS Repo" at "http://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies += "com.google.caliper" % "caliper" % "0.5-rc1"

libraryDependencies += "com.google.guava" % "guava" % "13.0.1"

libraryDependencies += "com.google.code.gson" % "gson" % "2.2.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
 
libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0"

libraryDependencies += "com.typesafe.akka" % "akka-remote_2.10" % "2.1.0"

fork in run := true            

javaOptions in run <++= (fullClasspath in Runtime) map {cp => Seq("-cp", sbt.Build.data(cp).mkString(if (util.Properties.isWin) ";" else ":"))}

javaOptions in run += "-Xmx1G" 

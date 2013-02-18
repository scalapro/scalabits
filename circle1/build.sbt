
name := "circle1"

version := "1.0"

scalaVersion := "2.9.2"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"

resolvers += "Sonatype OSS Repo" at "http://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies += "com.google.caliper" % "caliper" % "0.5-rc1"

libraryDependencies += "com.google.guava" % "guava" % "13.0.1"

libraryDependencies += "com.google.code.gson" % "gson" % "2.2.2"

fork in run := true            

javaOptions in run <++= (fullClasspath in Runtime) map {cp => Seq("-cp", sbt.Build.data(cp).mkString(if (util.Properties.isWin) ";" else ":"))}

javaOptions in run += "-Xmx1G" 

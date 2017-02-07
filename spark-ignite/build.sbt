name := "spark-ignite"

version := "1.0"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  "Gridgain Repository" at "http://gridgainsystems.com/nexus/content/repositories/external/",
  "Jcenter Repository" at "https://jcenter.bintray.com")

libraryDependencies ++= Seq(
   "org.apache.ignite" % "ignite-spark" % "1.8.0")
name := "sqsSparkConnector"

version := "2.0"

scalaVersion := "2.10.2"

libraryDependencies ++=Seq("org.apache.spark" %% "spark-core" % "1.0.0",
                          "org.apache.spark" %% "spark-streaming" % "1.0.0",
                         "org.apache.hadoop" % "hadoop-client" % "2.2.0",
                         "com.amazonaws" % "aws-java-sdk" % "1.0.002")



resolvers ++= Seq("Akka Repository" at "http://repo.akka.io/releases/", "Spray Repository" at "http://repo.spray.cc/",
  "Hadoop Repository" at "http://mirrors.ibiblio.org/maven2/")

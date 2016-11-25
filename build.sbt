val app = crossProject.settings(
  unmanagedSourceDirectories in Compile +=
    baseDirectory.value  / "shared" / "main" / "scala",
  libraryDependencies ++= Seq(
    "com.lihaoyi" %%% "scalatags" % "0.6.2",
    "com.lihaoyi" %%% "upickle" % "0.4.4"
  ),
  scalaVersion := "2.11.5"
).jsSettings(
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.1"
  )
).jvmSettings(
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http-experimental" % "2.4.11",
    "com.typesafe.akka" %% "akka-actor" % "2.4.12",
    "org.webjars" % "bootstrap" % "3.2.0",
    "com.github.cb372" %% "scalacache-guava" % "0.9.3",

    "com.typesafe" % "ssl-config-core_2.11" % "0.2.1",
    "com.eed3si9n" %% "gigahorse-core" % "0.1.1",


    "io.circe" %% "circe-core" % "0.6.1",
    "io.circe" %% "circe-generic" % "0.6.1",
    "io.circe" %% "circe-parser" % "0.6.1"
  )
)

lazy val appJS = app.js
lazy val appJVM = app.jvm.settings(
  (resources in Compile) += (fastOptJS in (appJS, Compile)).value.data
)

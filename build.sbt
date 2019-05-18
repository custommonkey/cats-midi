name := "cats-midi"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.github.alexarchambault" %% "scalacheck-shapeless_1.13" % "1.1.8" % Test,
  "eu.timepit"                 %% "refined"                   % "0.9.5",
  "eu.timepit"                 %% "refined-scalacheck"        % "0.9.5" % Test,
  "io.github.amrhassan"        %% "scalacheck-cats"           % "0.4.0",
  "org.typelevel"              %% "cats-core"                 % "1.6.0",
  "org.typelevel"              %% "cats-testkit"              % "1.6.0" % Test
)

//wartremoverErrors in Compile ++= Warts.all

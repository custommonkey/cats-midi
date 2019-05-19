name := "cats-midi"

version := "0.1"

scalaVersion := "2.12.8"

resolvers += Resolver.bintrayRepo("cibotech", "public")

libraryDependencies ++= Seq(
  "com.github.alexarchambault" %% "scalacheck-shapeless_1.13" % "1.1.8" % Test,
  "eu.timepit"                 %% "refined"                   % "0.9.5",
  "eu.timepit"                 %% "refined-scalacheck"        % "0.9.5" % Test,
  "io.github.amrhassan"        %% "scalacheck-cats"           % "0.4.0",
  "org.typelevel"              %% "cats-core"                 % "1.6.0",
  "org.typelevel"              %% "cats-testkit"              % "1.6.0" % Test,
  "com.cibo"                   %% "evilplot"                  % "0.6.3",
  "com.github.mpilquist"       %% "simulacrum"                % "0.16.0"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

//wartremoverErrors in Compile ++= Warts.all

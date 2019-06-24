name := "cats-midi"

version := "0.1"

scalaVersion := "2.12.8"

resolvers += Resolver.bintrayRepo("cibotech", "public")

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3")

libraryDependencies ++= Seq(
  "com.github.alexarchambault" %% "scalacheck-shapeless_1.13" % "1.1.8" % Test,
  "eu.timepit"                 %% "refined"                   % "0.9.8",
  "eu.timepit"                 %% "refined-scalacheck"        % "0.9.8" % Test,
  "io.github.amrhassan"        %% "scalacheck-cats"           % "0.4.0",
  "org.typelevel"              %% "cats-core"                 % "1.6.0",
  "org.typelevel"              %% "cats-testkit"              % "1.6.0" % Test,
  "com.cibo"                   %% "evilplot"                  % "0.6.3",
  "com.github.mpilquist"       %% "simulacrum"                % "0.19.0"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

scalafmtOnCompile := true

//wartremoverErrors in Compile ++= Warts.all

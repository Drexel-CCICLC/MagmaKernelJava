name := "MagmaKernelJava"

version := "0.1"

scalaVersion := "2.13.1"

mainClass := Some("com.meti.core.CompileTask")

libraryDependencies += "org.junit.jupiter" % "junit-jupiter-api" % "5.+" % Test
libraryDependencies += "org.junit.jupiter" % "junit-jupiter-engine" % "5.+" % Test
libraryDependencies += "org.junit.jupiter" % "junit-jupiter-params" % "5.+" % Test

// https://mvnrepository.com/artifact/org.mockito/mockito-all
libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19" % Test

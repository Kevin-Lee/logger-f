logLevel := sbt.Level.Warn

addSbtPlugin("org.foundweekends" % "sbt-bintray" % "0.5.6")

addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.4.10")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.6.0")

addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.2.7")

addSbtPlugin("io.kevinlee" % "sbt-devoops" % "1.0.3")

addSbtPlugin("org.scalameta" % "sbt-mdoc" % "2.2.8" )

addSbtPlugin("io.kevinlee" % "sbt-docusaur" % "0.3.0")

addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.5.1")

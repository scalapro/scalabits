scalabits
=========

Bits of useful scala code to learn from.

Ask David or Azad to add you as collaborators.

After cloning -

10/31/2013 - use sbt 0.13.0. sbteclipse plugin version used not compatible with sbt 0.12.

cp branch.sh.template to branch.sh and cutomize it to reflect your environment.
. branch.sh
[Or equivalent on Windows.]

For each project, e.g. circle1:

cd circle1
sbt
> update-classifiers 
> compile
> test:compile

Note. update-classifiers downloads sources and javadocs

sbt eclipse # Creates .project and .classpath for Eclipse.

Then -

cd .. # The root of all the projects.

Bring up Eclipse.

Import the projects into Eclipse.

Verified working with Eclipse version:

  Scala IDE build of Eclipse SDK
  Build id: 3.0.1-vfinal-20130718-1727-Typesafe
  Eclipse SDK
  Version: 3.7.2
  [included built-in update sites:
  Scala IDE - http://download.scala-ide.org/sdk/e37/scala210/stable/site 
  The Eclipse Project Updates - http://download.eclipse.org/eclipse/updates/3.7]

  ScalaTest plugin for Scala IDE 2.9.3.v-3-2 10-201310190806-60feeb3 
  manually installed

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


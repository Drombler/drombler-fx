#!/bin/bash          
mvn archetype:generate -DarchetypeGroupId=org.drombler.fx -DarchetypeArtifactId=drombler-fx-maven-archetype-application -DarchetypeVersion=${project.version} -DgroupId=com.mycompany.test -DartifactId=test -Dversion=0.1.0-SNAPSHOT -DbrandingId=my-application

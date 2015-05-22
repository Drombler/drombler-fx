#!/bin/bash          
cd test-application
java -Djavafx.verbose=true -Dbinary.css=false -Djava.util.logging.config.file=target/deployment/standalone/conf/logging.properties -jar target/deployment/standalone/bin/foo.jar --userdir target/userdir

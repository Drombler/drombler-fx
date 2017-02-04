#!/bin/bash          
cd test-application
java -Duser.language=en -Duser.country=US -Djavafx.verbose=true -Dbinary.css=false -Djava.util.logging.config.file=target/deployment/standalone/conf/logging.properties -jar target/deployment/standalone/bin/my-application.jar --userdir target/userdir

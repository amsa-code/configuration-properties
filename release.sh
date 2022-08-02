#!/bin/bash
set -e 
if [ $# -ne 1 ]
then
  echo "Usage: ./`basename $0` <VERSION>"
  # `basename $0` is the script's filename.
  exit 1 
fi
VERSION=$1
git pull origin master
mvn versions:set -DnewVersion=$VERSION -DgenerateBackupPoms=false
## test build works
mvn clean install
git commit -am "prepare for release $VERSION"
git tag -a $VERSION -m "$VERSION"
git push origin $VERSION
mvn deploy
mvn versions:set -DnextSnapshot=true -DgenerateBackupPoms=false
git commit -am "set versions to next snapshot"
git push origin master

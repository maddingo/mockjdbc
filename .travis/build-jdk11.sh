#!/usr/bin/env bash
set -ev
export MAVEN_OPTS="--add-modules ALL-SYSTEM"
if [ "${TRAVIS_PULL_REQUEST}" = "false" ]; then
  mkdir -p $HOME/.m2
  cp .travis/settings.xml $HOME/.m2/settings.xml
  mvn -B clean install sonar:sonar site site-deploy
else
  mvn -B clean verify
fi
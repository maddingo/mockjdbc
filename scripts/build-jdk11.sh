#!/usr/bin/env bash
set -ev
if [ "${TRAVIS_PULL_REQUEST}" = "false" ]; then
  MAVEN_OPTS="--add-modules ALL-SYSTEM" mvn clean install sonar:sonar site site-deploy
else
  MAVEN_OPTS="--add-modules ALL-SYSTEM" mvn clean verify
fi
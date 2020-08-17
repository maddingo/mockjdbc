#!/usr/bin/env bash
set -ev
export MAVEN_OPTS="--add-modules ALL-SYSTEM"
if [ "${TRAVIS_PULL_REQUEST}" = "false" ]; then
   mvn -B clean install sonar:sonar
else
  mvn -B clean verify
fi
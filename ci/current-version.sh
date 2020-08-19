#!/bin/bash

GIT_REV_LIST=`git rev-list --tags --max-count=1`
GIT_REV_LAST=`git rev-parse HEAD`
VERSION='0.0.0'
if [[ -n $GIT_REV_LIST ]]; then
    VERSION=`git describe --tags $GIT_REV_LIST`
fi
echo $VERSION
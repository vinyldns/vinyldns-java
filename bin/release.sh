#!/usr/bin/env bash
#
# Copyright 2018 Comcast Cable Communications Management, LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

export GPG_TTY=$(tty)
DIR=$( cd $(dirname $0) ; pwd -P )
cd "${DIR}/../"

function usage {
    printf "usage: release.sh [OPTIONS]\n\n"
    printf "release vinyldns-java artifact to OSS Sonatype\n\n"
    printf "options:\n"
    printf "\t-f, --full performs a full release as opposed to a SNAPSHOT\n"
}

SNAPSHOT=1
while [ "$1" != "" ]; do
    case "$1" in
        -f | --full ) SNAPSHOT=0;  ;;
        * ) usage; exit;;
    esac
    shift
done

if [[ "$SNAPSHOT" == 1 ]]; then
    printf "\nperforming a SNAPSHOT release\n"
    mvn clean deploy -P release,ossrh
else
    printf "\nperforming a full release\n"
    mvn -e release:clean release:prepare -P release,ossrh
    mvn -e release:perform -P release,ossrh
fi

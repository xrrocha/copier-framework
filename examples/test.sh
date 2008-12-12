#!/bin/bash

. env.sh

script="${1:-epg.yaml}"

java -Djava.util.logging.config.file=logging.properties plenix.copier.Main "$script"

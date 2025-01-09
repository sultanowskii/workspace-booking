#!/bin/bash

cat scripts/functions.sql scripts/triggers.sql | sed "s/'/''/g" | sed 's/\$\$/'\''/g' > scripts/tmp.sql

cat scripts/schema.sql scripts/indexes.sql scripts/tmp.sql > app/src/main/resources/schema.sql

rm scripts/tmp.sql

#!/bin/bash
echo "sleeping for 5 seconds"
sleep 5

# shellcheck disable=SC2046
# shellcheck disable=SC2006
echo mongo_setup.sh time now: `date +"%T" `
mongo --host mongo-primary:27017 <<EOF

  db = db.getSiblingDB('admin')
  db.auth( "d_easy_admin", "123qwe" )
  let cfg = {
    _id: "rs0",
    members: [
        { _id: 0, host:"mongo-primary:27017" }
    ]
  };
  let result = rs.initiate(cfg);
  printjson(result);

EOF

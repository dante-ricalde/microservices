#!/bin/bash
# wait-for-config-server.sh
echo "$@"

execJar="$1"
#shift
cmd="$@"
echo "response $response"
until [ "$response" = "200" ]
do
	response=$(curl --write-out %{http_code} --silent --output /dev/null --connect-timeout 5 http://config-server-test:8888/env)
	sleep 2
done

echo "Config Server is ready"
echo "$execJar"
exec $cmd
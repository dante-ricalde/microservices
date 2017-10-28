#!/bin/bash
# wait-for-config-server.sh
echo "$@"

configServerUrl="$1"
shift
cmd="$@"
echo "response $response"
until [ "$response" = "200" ]
do
	response=$(curl --write-out %{http_code} --silent --output /dev/null --connect-timeout 5 $configServerUrl)
	sleep 2
done

echo "Config Server is ready"
#echo "$execJar"
exec $cmd
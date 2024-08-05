#!/usr/bin/env sh
set -eu

envsubst '${PORT} ${SPRING_PORT} ${SOCKET_PORT}' < /etc/nginx/nginx.conf.template > /etc/nginx/nginx.conf

exec "$@"
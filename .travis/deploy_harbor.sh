#!/usr/bin/env bash
set -e
TAG=$(date +%Y%m%d%H%M)
docker login ${HARBOR_REGISTRY} -u ${HARBOR_USER} -p ${HARBOR_TOKEN}
docker build -t ${HARBOR_REGISTRY}/${HARBOR_NAMESPACE}/multibanking-service:${TAG} multibanking-server/
docker push ${HARBOR_REGISTRY}/${HARBOR_NAMESPACE}/multibanking-service:${TAG}

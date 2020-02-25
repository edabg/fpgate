#!/bin/sh
BIN_PATH=${INSTALL_PATH}/bin
echo Starting EDA FPGate Server
cd ${BIN_PATH}
java -jar ${BIN_PATH}/FPGateSrv.jar &


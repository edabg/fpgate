@echo off
rem SET BIN_PATH=%~dp0
SET BIN_PATH=${INSTALL_PATH}
echo Starting EDA FPGate Server
SET DRIVE=%BIN_PATH:~0,2%
%DRIVE%
CD %BIN_BATH%
start FPGateSrv.jar
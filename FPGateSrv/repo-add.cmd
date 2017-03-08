@echo off
set MVN=C:\Program Files\NetBeans 8.1\java\maven\bin\mvn.bat
set JAVA_HOME=C:\Program Files\Java\jre1.8.0_101
rem C:\Documents and Settings\nikolabintev\.m2\repository\com\jacob\jacob\1.18-M2\jacob-1.18-M2.jar
set FPGateRoot=W:\www - CO.edabg.com\Apps\fp3530\FPGateSrv
set REPOSITORY=W:\www - CO.edabg.com\Apps\fp3530\FPGateSrv\FPGateSrv\src\main\resources\repo

set FISCAL_VERSION=1.1.1
set JACOB_VERSION=1.18-M2

set FISCAL_APIS_PATH=%FPGateRoot%\Java-Fiscal-Device-API\apis

set FISCAL_LIB_PATH=%FPGateRoot%\Java-Fiscal-Device-API\build\libs
set JACOB_LIB_PATH=%FPGateRoot%\FPGateSrv\lib\packages
set RXTX_LIB_PATH=%FPGateRoot%\FPGateSrv\lib\packages\rxtx
set NRJAVASERIAL_LIB_PATH=%FPGateRoot%\FPGateSrv\lib\packages\nrjavaserial

if "%1"=="fiscal" goto com_fiscal
if "%1"=="jacob" goto com_jacob
if "%1"=="comm" goto javax_comm
if "%1"=="rxtx" goto rxtx
if "%1"=="nrjavaserial" goto nrjavaserial
goto usage

:javax_comm
echo Adding javax-comm-2.0.jar to repository ... 
call "%MVN%" install:install-file -Dfile="%FISCAL_APIS_PATH%\javax-comm-2.0.jar" -DgroupId=javax -DartifactId=comm -Dversion=2.0 -Dpackaging=jar -DlocalRepositoryPath="%REPOSITORY%"
goto end

:nrjavaserial
echo Adding nrjavaserial to repository ... 
call "%MVN%" install:install-file -Dfile="%NRJAVASERIAL_LIB_PATH%\nrjavaserial-3.12.0.jar" -DgroupId=com.neuronrobotics -DartifactId=nrjavaserial -Dversion=3.12.0 -Dpackaging=jar -DlocalRepositoryPath="%REPOSITORY%"
goto end

:rxtx
echo Adding rxtx-2.2pre2 to repository ... 
call "%MVN%" install:install-file -Dfile="%RXTX_LIB_PATH%\rxtx-2.2pre2.jar" -DgroupId=org.rxtx -DartifactId=rxtx -Dversion=2.2pre2 -Dpackaging=jar -DlocalRepositoryPath="%REPOSITORY%"
rem call "%MVN%" install:install-file -Dfile="%RXTX_LIB_PATH%\rxtx-2.2-20081207.jar" -DgroupId=org.rxtx -DartifactId=rxtx -Dversion=2.2-20081207 -Dpackaging=jar -DlocalRepositoryPath="%REPOSITORY%"
rem echo Adding fiscal-device-%JACOB_VERSION%_src.zip to repository ... 
rem call "%MVN%" install:install-file -Dfile="%RXTX_LIB_PATH%\rxtx-2.2-20081207-sources.jar" -DgroupId=org.rxtx -DartifactId=rxtx -Dversion=%JACOB_VERSION% -Dpackaging=zip -Dclassifier=sources -DlocalRepositoryPath="%REPOSITORY%"

rem call "%MVN%" install:install-file -Dfile="%RXTX_LIB_PATH%\x64\rxtxParallel.dll" -DgroupId=org.rxtx -DartifactId=rxtx -Dversion=2.2-20081207 -Dpackaging=dll -DlocalRepositoryPath="%REPOSITORY%" -Dclassifier=x64
rem call "%MVN%" install:install-file -Dfile="%RXTX_LIB_PATH%\x64\rxtxSerial.dll" -DgroupId=org.rxtx -DartifactId=rxtx -Dversion=2.2-20081207 -Dpackaging=dll -DlocalRepositoryPath="%REPOSITORY%" -Dclassifier=x64
call "%MVN%" install:install-file -Dfile="%RXTX_LIB_PATH%\x64\rxtxSerial.dll" -DgroupId=org.rxtx -DartifactId=rxtx -Dversion=2.2pre2 -Dpackaging=dll -DlocalRepositoryPath="%REPOSITORY%" -Dclassifier=x64
rem call "%MVN%" install:install-file -Dfile="%RXTX_LIB_PATH%\x86\rxtxParallel.dll" -DgroupId=org.rxtx -DartifactId=rxtx -Dversion=2.2-20081207 -Dpackaging=dll -DlocalRepositoryPath="%REPOSITORY%" -Dclassifier=x86
rem call "%MVN%" install:install-file -Dfile="%RXTX_LIB_PATH%\x86\rxtxSerial.dll" -DgroupId=org.rxtx -DartifactId=rxtx -Dversion=2.2-20081207 -Dpackaging=dll -DlocalRepositoryPath="%REPOSITORY%" -Dclassifier=x86
call "%MVN%" install:install-file -Dfile="%RXTX_LIB_PATH%\x86\rxtxSerial.dll" -DgroupId=org.rxtx -DartifactId=rxtx -Dversion=2.2pre2 -Dpackaging=dll -DlocalRepositoryPath="%REPOSITORY%" -Dclassifier=x86

:com_fiscal
echo Adding fiscal-device-%FISCAL_VERSION%.jar to repository ... 
call "%MVN%" install:install-file -Dfile="%FISCAL_LIB_PATH%\fiscal-device-%FISCAL_VERSION%.jar" -DgroupId=com.taliter -DartifactId=fiscal -Dversion=%FISCAL_VERSION% -Dpackaging=jar -DlocalRepositoryPath="%REPOSITORY%"
echo Adding fiscal-device-%FISCAL_VERSION%-sources.jar to repository ... 
call "%MVN%" install:install-file -Dfile="%FISCAL_LIB_PATH%\fiscal-device-%FISCAL_VERSION%-sources.jar" -DgroupId=com.taliter -DartifactId=fiscal -Dversion=%FISCAL_VERSION% -Dpackaging=jar -Dclassifier=sources -DlocalRepositoryPath="%REPOSITORY%"
echo Adding fiscal-device-%FISCAL_VERSION%-doc.jar to repository ... 
call "%MVN%" install:install-file -Dfile="%FISCAL_LIB_PATH%\fiscal-device-%FISCAL_VERSION%-doc.jar" -DgroupId=com.taliter -DartifactId=fiscal -Dversion=%FISCAL_VERSION% -Dpackaging=jar -Dclassifier=doc -DlocalRepositoryPath="%REPOSITORY%"
goto end

:com_jacob
rem c:mvn install:install-file -Dfile=..\..\..\..\lib\jacob-1.18-M2\jacob.jar -DgroupId=com.jacob -DartifactId=jacob -Dversion=1.18-M2 -Dpackaging=jar -DlocalRepositoryPath=.

echo Adding jacob-%JACOB_VERSION%.zip to repository ... 
rem call "%MVN%" install:install-file -Dfile="%JACOB_LIB_PATH%\jacob-%JACOB_VERSION%.zip" -DgroupId=com.jacob -DartifactId=jacob -Dversion=%JACOB_VERSION% -Dpackaging=zip -DlocalRepositoryPath="%REPOSITORY%"
call "%MVN%" install:install-file -Dfile="%JACOB_LIB_PATH%\jacob\%JACOB_VERSION%\jacob-%JACOB_VERSION%.jar" -DgroupId=com.jacob -DartifactId=jacob -Dversion=%JACOB_VERSION% -Dpackaging=jar -DlocalRepositoryPath="%REPOSITORY%"
echo Adding fiscal-device-%JACOB_VERSION%_src.zip to repository ... 
call "%MVN%" install:install-file -Dfile="%JACOB_LIB_PATH%\jacob\%JACOB_VERSION%\jacob-%JACOB_VERSION%-sources.zip" -DgroupId=com.jacob -DartifactId=jacob -Dversion=%JACOB_VERSION% -Dpackaging=zip -Dclassifier=sources -DlocalRepositoryPath="%REPOSITORY%"

call "%MVN%" install:install-file -Dfile="%JACOB_LIB_PATH%\jacob\%JACOB_VERSION%\jacob-%JACOB_VERSION%-x64.dll" -DgroupId=com.jacob -DartifactId=jacob -Dversion=1.18-M2 -Dpackaging=dll -DlocalRepositoryPath="%REPOSITORY%" -Dclassifier=x64
call "%MVN%" install:install-file -Dfile="%JACOB_LIB_PATH%\jacob\%JACOB_VERSION%\jacob-%JACOB_VERSION%-x86.dll" -DgroupId=com.jacob -DartifactId=jacob -Dversion=1.18-M2 -Dpackaging=dll -DlocalRepositoryPath="%REPOSITORY%" -Dclassifier=x86


rem echo Adding fiscal-device-%JACOB_VERSION%-doc.jar to repository ... 
rem call "%MVN%" install:install-file -Dfile="%JACOB_LIB_PATH%\fiscal-device-%JACOB_VERSION%-doc.jar" -DgroupId=com.taliter -DartifactId=fiscal -Dversion=%JACOB_VERSION% -Dpackaging=jar -Dclassifier=doc -DlocalRepositoryPath="%REPOSITORY%"
goto end

:usage
echo.
echo Usage: repo-add jacob^|fiscal
echo.
echo jacob - add jacob-%JACOB_VERSION% to local repository 
echo fiscal - add fiscal-%FISCAL_VERSION% to local repository 
echo comm - add javax-comm-2.0.jar to local repository 
echo rxtx - add rxtx-2.2pre2 to local repository 
echo.
echo Local repository path: %REPOSITORY%
echo Java Home            : %JAVA_HOME%
echo Maven Binary         : %MVN%
goto end

:end

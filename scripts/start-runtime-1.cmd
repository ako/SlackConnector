rem script to start mendix application from a windows cmd prompt

FOR /F "tokens=1,* delims==" %%a IN (%HOME%\.slackconnector.cfg) DO (set %%a=%%b)

set NETCAT_PATH=c:\programs\netcat\
set JAVA_PATH=C:\Program Files\Java\jdk1.8.0_112\bin
set APP_PATH=C:\Projects\mendix\SlackConnector
set APP_PATH_UX=C:/Projects/mendix/SlackConnector
set MX_INSTALL_PATH=C:\Program Files\Mendix\7.0.1
set MX_INSTALL_PATH_UX=C:/Program Files/Mendix/7.0.1
set M2EE_ADMIN_PASS=1
set M2EE_ADMIN_PASS_BASE64=MQ==
set M2EE_MONITORING_PASS=1
set M2EE_MONITORING_PASS_BASE64=MQ==
set M2EE_ADMIN_PORT=8092
set M2EE_RUNTIME_PORT=8082
set APP_LOG_PATH=\temp\app-1.log
set APP_LOG_PATH_UX=/temp/app-1.log
set APP_CONSTANTS={'TestSlackConnector.AuthenticationToken': '%authToken%','AppCloudServices.LogNode':'AppCloudServices','AppCloudServices.EnvironmentUUID':'','AppCloudServices.EnvironmentPassword':'','AppCloudServices.OpenIdProvider':'','AppCloudServices.OpenIdEnabled':false}

rem
rem start mendix runtime launcher
rem

REM start /b "runtime" "%JAVA_PATH%\java.exe" -Djava.net.preferIPv4Stack=true ^
REM  -DMX_LOG_LEVEL=INFO -Djava.library.path="%MX_INSTALL_PATH%/runtime/lib/x64;%APP_PATH%/deployment/model/lib/userlib" ^
REM  -Dfile.encoding=UTF-8 -Djava.io.tmpdir="%APP_PATH%/deployment/data/tmp" -Djava.security.manager ^
REM  -Djava.security.policy=="%APP_PATH%\deployment\data\.policy" ^
REM  -jar "%MX_INSTALL_PATH%\runtime\launcher\runtimelauncher.jar" "%APP_PATH%\deployment"

start /b "runtime" "%JAVA_PATH%\java.exe"  ^
  -DMX_LOG_LEVEL=INFO ^
  -jar "%MX_INSTALL_PATH%\runtime\launcher\runtimelauncher.jar" "%APP_PATH%\deployment"

sleep 5

rem
rem create logfile subscriber
rem


set CMD='{"action": "create_log_subscriber", "params": {"max_rotation": 7, "name": "MXLOGSUBS", "filename": "%APP_LOG_PATH_UX%", "autosubscribe": "INFO", "type": "file", "max_size": 10485760}}'
curl -i -H "X-M2EE-Authentication: %M2EE_MONITORING_PASS_BASE64%" -H "Content-Type: application/json" -X POST http://localhost:%M2EE_ADMIN_PORT%/ -d %CMD%

rem
rem start tcp logsubscriber
rem

rem start /b "netcat" cmd /c "%NETCAT_PATH%\nc.exe -l -p 31337 | jq '.'"

set CMD="{'action': 'create_log_subscriber', 'params': {'type': 'tcpjson', 'name': 'TCPJSONLog','autosubscribe': 'INFO', 'host': '127.0.0.1', 'port':31337}}"
rem curl -i -H "X-M2EE-Authentication: %M2EE_ADMIN_PASS_BASE64%" -H "Content-Type: application/json" -X POST http://localhost:%M2EE_ADMIN_PORT%/ -d %CMD%

rem
rem start runtime logging
rem

set CMD="{'action': 'start_logging'}" 
curl -i -H "X-M2EE-Authentication: %M2EE_ADMIN_PASS_BASE64%" -H "Content-Type: application/json" -X POST http://localhost:%M2EE_ADMIN_PORT%/ -d %CMD%

rem
rem set loglevels
rem

set CMD="{ 'action':'set_log_level', 'params': {'nodes' : [ { 'name':'ConnectionBus', 'level':'INFO'}, { 'name':'ActionManager', 'level':'INFO'} ], 'force':True} }"
curl -i -H "X-M2EE-Authentication: %M2EE_ADMIN_PASS_BASE64%" -H "Content-Type: application/json" -X POST http://localhost:%M2EE_ADMIN_PORT%/ -d %CMD%

rem
rem configure appcontainer
rem

set CMD="{'action': 'update_appcontainer_configuration', 'params': {'runtime_port': %M2EE_RUNTIME_PORT%, 'runtime_listen_addresses': '*', 'runtime_jetty_options': {'use_blocking_connector': false}}}"
curl -i -H "X-M2EE-Authentication: %M2EE_ADMIN_PASS_BASE64%" -H "Content-Type: application/json" -X POST http://localhost:%M2EE_ADMIN_PORT%/ -d %CMD%

rem
rem update configuration
rem

set CMD="{'action': 'update_configuration', 'params': {'DatabaseHost': '127.0.0.1:5432', 'DTAPMode': 'D', 'MicroflowConstants': %APP_CONSTANTS%, 'ApplicationRootUrl':'http://localhost:%M2EE_RUNTIME_PORT%/','BasePath': '%APP_PATH_UX%/Deployment', 'DatabaseUserName': 'mx', 'DatabasePassword': 'mx', 'DatabaseName': 'dev5', 'RuntimePath': '%MX_INSTALL_PATH_UX%/runtime', 'DatabaseType': 'PostgreSQL', 'ScheduledEventExecution': 'NONE'}}"
curl -i -H "X-M2EE-Authentication: %M2EE_ADMIN_PASS_BASE64%" -H "Content-Type: application/json" -X POST http://localhost:%M2EE_ADMIN_PORT%/ -d %CMD%

rem
rem execute ddl commands
rem

rem set CMD="{'action': 'execute_ddl_commands', 'params': {}}"
rem curl -i -H "X-M2EE-Authentication: %M2EE_ADMIN_PASS_BASE64%" -H "Content-Type: application/json" -X POST http://localhost:%M2EE_ADMIN_PORT%/ -d %CMD%

rem
rem start app
rem

set CMD="{'action': 'start'}"
curl -i -H "X-M2EE-Authentication: %M2EE_ADMIN_PASS_BASE64%" -H "Content-Type: application/json" -X POST http://localhost:%M2EE_ADMIN_PORT%/ -d %CMD%

tail -f %APP_LOG_PATH%

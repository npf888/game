//是否是调试模式
config.debug=true;
//服务器的版本号
config.version="0.1.0.0"
//服务器名称
config.serverName = "LogServer"
//
config.serverHost="127.0.0.1"
//
config.serverDomain="test.d.mop.com"
//服务绑定的IP
config.bindIp="127.0.0.1";
//服务绑定的端口 //线上 9890
config.port=7001;
//定时创建每日日志表任务启动的延迟时间
config.createTableTaskDelay = 1000*60*24;
//定时创建每日日志表任务的执行周期
config.createTableTaskPeriod = 1000*60*24;
//配置IBatis的配置文件名
config.ibatisConfig="log_ibatis_config.xml";
//配置消息类型识别器
config.messageRecognizer=new com.logserver.LogMessageRecognizer();
//配置消息处理器
config.logMessageHandler=new com.logserver.LogMessageHandler();
//配置建立日志表的策略
config.tableCreator = new com.logserver.createtable.TodayAndTommorowTableCreator();
//服务器ID 
config.serverId="10003";
//服务器索引;
config.serverIndex=25;

/*
 *配置Telnet 
 */
config.telnetServerName="LogServer_telnet";
config.telnetBindIp="127.0.0.1";
config.telnetPort=7001;

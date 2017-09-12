package com.citic.server.cbrc.listener;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.handler.chain.ChainedIoHandler;
import org.apache.mina.handler.chain.IoHandlerCommand;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.citic.server.SpringContextHolder;
import com.citic.server.basic.AbstractListenTask;
import com.citic.server.http.codec.HttpProtocolCodecFactory;
import com.citic.server.http.service.HttpProtocolChainedIoHandler;
import com.citic.server.http.service.HttpProtocolIoHandlerCommand;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.conf.ConfigConstants;
import com.citic.server.runtime.conf.HttpListenCommand;

/**
 * HTTP监听服务
 * 
 * @author Liu Xuanfei
 * @date 2016年9月19日 上午11:47:23
 */
@Component("httpProtocolListenTask")
public class HttpProtocolListenTask extends AbstractListenTask implements ConfigConstants {
	private static final Logger logger = LoggerFactory.getLogger(HttpProtocolListenTask.class);
	
	@Autowired
	@Qualifier("httpNotFoundIoHandlerCommand")
	private IoHandlerCommand notFoundHandlerCommand;
	
	@Override
	public void startup() throws Exception {
		int port = ServerEnvironment.getHttpListenServerPort();
		
		IoAcceptor acceptor = new NioSocketAcceptor(); // TCP/IP
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new HttpProtocolCodecFactory(true)));
		acceptor.getFilterChain().addLast("exceutor", new ExecutorFilter(0, 200, 200, TimeUnit.SECONDS));
		
		acceptor.getSessionConfig().setReadBufferSize(4096); //
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60); //
		acceptor.getSessionConfig().setWriteTimeout(30); //
		
		ChainedIoHandler handler = new HttpProtocolChainedIoHandler();
		List<HttpListenCommand> commands = ServerEnvironment.getAllHttpListenCommand();
		if (commands != null && commands.size() > 0) {
			for (HttpListenCommand command : commands) {
				String clazz = (this.getServerType() == SERVER_TYPE_OUTER) ? command.getOuterClass() : command.getInnerClass();
				HttpProtocolIoHandlerCommand handlerCommand = (HttpProtocolIoHandlerCommand) SpringContextHolder.getBean(clazz);
				handlerCommand.register(command);
				handler.getChain().addLast("command-" + command.getId(), handlerCommand);
			}
		}
		handler.getChain().addLast("last", notFoundHandlerCommand);
		acceptor.setHandler(handler);
		acceptor.bind(new InetSocketAddress(port));
		
		logger.info("HTTP监听服务已启动，监听端口=[{}]", port);
	}
}

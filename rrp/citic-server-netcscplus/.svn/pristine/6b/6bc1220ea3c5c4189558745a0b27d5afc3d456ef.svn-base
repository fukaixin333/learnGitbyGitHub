package com.citic.server.basic.service;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.citic.server.runtime.RemoteAccessException;

/**
 * MINA远程访问服务
 * 
 * @author Liu Xuanfei
 * @date 2016年4月19日 上午9:34:08
 */
public abstract class AbstractRemoteAccessService implements IRemoteAccessService {
	
	/** 空闲时间(s) */
	protected static final int DEFAULT_IDLE_TIME = 180;
	
	/** 连接超时时间(s) */
	protected static final int DEFAULT_CONNECT_TIMEOUT = 60;
	
	/** 发送报文超时时间(s) */
	protected static final int DEFAULT_WRITE_TIMEOUT = 30;
	
	/** 读取响应超时时间(s) */
	protected static final int DEFAULT_READ_TIMEOUT = 30;
	
	/** 报文前缀固定的长度 */
	protected static final int DEFAULT_MESSAGE_PREFIX_LENGTH = 4;
	
	/** I/O连接器 */
	private final IoConnector connector;
	
	/** 远程通讯地址 */
	private SocketAddress remoteSocketAddress;
	
	public AbstractRemoteAccessService() {
		connector = new NioSocketConnector(10);
		// 过滤器
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(getProtocolCodecFactory()));
		connector.getFilterChain().addLast("exceutor", new ExecutorFilter(10, 200, 200, TimeUnit.SECONDS));
		// 初始化
		connector.getSessionConfig().setReadBufferSize(2048); // 缓冲
		connector.getSessionConfig().setUseReadOperation(true); // 启用read()操作
		connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, DEFAULT_IDLE_TIME); // 闲置时间
		connector.setConnectTimeoutMillis(DEFAULT_CONNECT_TIMEOUT * 1000L); // 连接超时时间(ms)
		
		// JMX监控（开放此功能需要Maven引入Apache Felix插件）
		//	<dependency>
		//      <groupId>org.apache.mina</groupId>
		//      <artifactId>mina-integration-jmx</artifactId>
		//      <version>2.0.9</version>
		//  </dependency>
		//  <!-- OSGi Apache实现 - 用于MINA集成JMX监控 -->
		//  <plugin>
		//      <groupId>org.apache.felix</groupId>
		//      <artifactId>maven-bundle-plugin</artifactId>
		//      <extensions>true</extensions>
		//  </plugin>
		
		//		try {
		//			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		//			IoServiceMBean connectorMBean = new IoServiceMBean(connector);
		//			ObjectName connectorName = new ObjectName(connector.getClass().getPackage().getName() + ":type=connector,name=" + this.getClass().getSimpleName());
		//			mBeanServer.registerMBean(connectorMBean, connectorName);
		//		} catch (MalformedObjectNameException e) {
		//			e.printStackTrace();
		//		} catch (NullPointerException e) {
		//			e.printStackTrace();
		//		} catch (InstanceAlreadyExistsException e) {
		//			e.printStackTrace();
		//		} catch (MBeanRegistrationException e) {
		//			e.printStackTrace();
		//		} catch (NotCompliantMBeanException e) {
		//			e.printStackTrace();
		//		}
	}
	
	/**
	 * 通讯报文编码解码器
	 * 
	 * @return
	 */
	public abstract ProtocolCodecFactory getProtocolCodecFactory();
	
	/**
	 * 远程通讯地址
	 * 
	 * @return 访问地址
	 */
	public abstract String getSocketAddressHost();
	
	/**
	 * 远程通讯端口号
	 * 
	 * @return 端口号
	 */
	public abstract int getSocketAddressPort();
	
	/**
	 * 向远程服务器发送请求报文
	 * 
	 * @param message 请求报文
	 * @param hostname 远程服务器地址
	 * @param port 远程服务器端口
	 * @return 远程服务器反馈的报文
	 * @throws RemoteAccessException
	 */
	@Override
	public String writeRequestMessage(String message) throws RemoteAccessException {
		IoSession session = null;
		try {
			ConnectFuture connFuture = connector.connect(getRemoteSocketAddress());
			session = connFuture.awaitUninterruptibly().getSession();
			if (session != null) {
				// 发送报文
				session.write(message);
				if (connFuture.awaitUninterruptibly(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)) {
					// 读取反馈
					ReadFuture readFuture = session.read();
					if (readFuture.awaitUninterruptibly(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)) {
						return (String) readFuture.getMessage();
					} else {
						throw new RemoteAccessException("响应超时");
					}
				} else {
					throw new RemoteAccessException("发送报文超时");
				}
			} else {
				throw new RemoteAccessException("连接超时");
			}
		} catch (RemoteAccessException e) {
			throw e;
		} catch (Exception e) {
			throw new RemoteAccessException(e.getMessage(), e);
		} finally {
			closeSession(session);
		}
	}
	
	public void closeSession(IoSession session) {
		if (session != null) {
			session.closeNow();
		}
		// connector.dispose(); // 不能销毁IoConnector
	}
	
	// 远程通讯地址
	public SocketAddress getRemoteSocketAddress() {
		if (remoteSocketAddress == null) {
			remoteSocketAddress = new InetSocketAddress(getSocketAddressHost(), getSocketAddressPort());
		}
		return remoteSocketAddress;
	}
}

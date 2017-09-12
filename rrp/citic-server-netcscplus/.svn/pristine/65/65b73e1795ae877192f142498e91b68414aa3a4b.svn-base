/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/07/14 00:34:11$
 */
package com.citic.server.logback;

import java.io.OutputStream;

import org.fusesource.jansi.AnsiConsole;

import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.util.EnvUtil;

/**
 * 某些情况下（例如 Eclipse 控制台），Jansi 无法获取屏幕信息，
 * 重写 Logback 默认的 Appender 以忽略异常信息。
 * 
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/14 00:34:11$
 */
public class ANSIConsoleAppender<E> extends ConsoleAppender<E> {
	
	private boolean withJansi = false;
	
	@Override
	public void setOutputStream(OutputStream outputStream) {
		if (EnvUtil.isWindows() && withJansi) {
			outputStream = AnsiConsole.wrapOutputStream(outputStream);
		}
		super.setOutputStream(outputStream);
	}
	
	@Override
	public void setWithJansi(boolean withJansi) {
		super.setWithJansi(false);
		this.withJansi = withJansi;
	}
	
	@Override
	public boolean isWithJansi() {
		return withJansi;
	}
}

// WindowsAnsiOutputStream 加载失败时，Jansi 会忽略 ANSI 序列并返回流。
// 但 Logback 试图直接对它进行初始化，以至于会抛出异常：Failed to create WindowsAnsiOutputStream. Falling back on the default stream.

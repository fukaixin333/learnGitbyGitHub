/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/07/14 00:49:47$
 */
package com.citic.server.logback;

import static com.citic.server.logback.ANSIConstants.DEFAULT;
import static com.citic.server.logback.ANSIConstants.DEFAULT_FG;
import static com.citic.server.logback.ANSIConstants.ESC_END;
import static com.citic.server.logback.ANSIConstants.ESC_START;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;

/**
 * 根据日志级别高亮修饰的文本
 * 
 * @see {@link ANSIConstants}
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/14 00:49:47$
 */
public class HighlightingCompositeConverter extends CompositeConverter<ILoggingEvent> {
	
	private static final String DEFAULT_PS1 = ESC_START + DEFAULT + DEFAULT_FG + ESC_END;
	
	@Override
	protected String transform(ILoggingEvent event, String intermediary) {
		StringBuilder sb = new StringBuilder();
		sb.append(ESC_START);
		sb.append(getForegroundCode(event));
		sb.append(getBackgroundCode(event));
		sb.append(ESC_END);
		sb.append(intermediary);
		sb.append(DEFAULT_PS1);
		return sb.toString();
	}
	
	protected String getForegroundCode(ILoggingEvent event) {
		Level level = event.getLevel();
		switch (level.toInt()) {
		case Level.ERROR_INT:
			return ANSIConstants.BOLD + ANSIConstants.RED_FG;
		case Level.WARN_INT:
			return ANSIConstants.MAGENTA_FG;
		case Level.DEBUG_INT:
			return ANSIConstants.CYAN_FG;
		case Level.TRACE_INT:
			return ANSIConstants.YELLOW_FG;
		default:
			return ANSIConstants.DEFAULT_FG;
		}
	}
	
	protected String getBackgroundCode(ILoggingEvent event) {
		Level level = event.getLevel();
		switch (level.toInt()) {
		case Level.ERROR_INT:
			return ANSIConstants.WHITE_BG;
		case Level.WARN_INT:
		case Level.DEBUG_INT:
		case Level.TRACE_INT:
		default:
			return ANSIConstants.DEFAULT_BG;
		}
	}
}

/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/07/12 19:45:17$
 */
package com.citic.server.utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.PrintGraphics;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.print.PrinterGraphics;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.plaf.basic.BasicGraphicsUtils;

/**
 * 渲染工具
 * 
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/12 19:45:17$
 */
public class RenderingUtils {
	private static final String PROP_DESKTOPHINTS = "awt.font.desktophints";
	
	public static void drawString(Graphics g, String text, int x, int y) {
		Graphics2D g2d = (Graphics2D) g;
		Map<?, ?> oldRenderingHints = installDesktopHints(g2d);
		BasicGraphicsUtils.drawStringUnderlineCharAt(g2d, text, -1, x, y);
		if (oldRenderingHints != null) {
			g2d.addRenderingHints(oldRenderingHints);
		}
	}
	
	private static Map<RenderingHints.Key, Object> installDesktopHints(Graphics2D g2d) {
		Map<RenderingHints.Key, Object> oldDesktopHints = null;
		Map<?, ?> desktopHints = desktopHints(g2d);
		if (desktopHints != null && !desktopHints.isEmpty()) {
			oldDesktopHints = new HashMap<RenderingHints.Key, Object>(desktopHints.size());
			RenderingHints.Key key;
			for (Iterator<?> it = desktopHints.keySet().iterator(); it.hasNext();) {
				key = (RenderingHints.Key) it.next();
				oldDesktopHints.put(key, g2d.getRenderingHint(key));
			}
			g2d.addRenderingHints(desktopHints);
		}
		return oldDesktopHints;
	}
	
	private static Map<?, ?> desktopHints(Graphics2D g2d) {
		if (isPrinting(g2d)) {
			return null;
		}
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		GraphicsDevice device = g2d.getDeviceConfiguration().getDevice();
		Map<?, ?> desktopHints = (Map<?, ?>) toolkit.getDesktopProperty(PROP_DESKTOPHINTS + "." + device.getIDstring());
		if (desktopHints == null) {
			desktopHints = (Map<?, ?>) toolkit.getDesktopProperty(PROP_DESKTOPHINTS);
		}
		
		if (desktopHints != null) {
			Object aaHint = desktopHints.get(RenderingHints.KEY_TEXT_ANTIALIASING);
			if (aaHint == RenderingHints.VALUE_TEXT_ANTIALIAS_OFF || aaHint == RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT) {
				desktopHints = null;
			}
		}
		
		return desktopHints;
	}
	
	private static boolean isPrinting(Graphics g) {
		return g instanceof PrintGraphics || g instanceof PrinterGraphics;
	}
}

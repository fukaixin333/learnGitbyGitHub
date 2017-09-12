/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/07/12 16:54:32$
 */
package com.citic.server.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 图形处理工具
 * 
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/12 16:54:32$
 */
public class GraphicsUtilities {
	
	public static GraphicsConfiguration getGraphicsConfiguration() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	}
	
	public static BufferedImage createCompatibleImage(BufferedImage image) {
		GraphicsConfiguration gc;
		if (GraphicsEnvironment.isHeadless()) {
			gc = image.createGraphics().getDeviceConfiguration();
		} else {
			gc = getGraphicsConfiguration();
		}
		return gc.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());
	}
	
	public static BufferedImage createWatermarkImage(BufferedImage image, String watermark, Color color) {
		return createWatermarkImage(image, watermark, color, 0);
	}
	
	public static BufferedImage createWatermarkImage(BufferedImage image, String watermark, Color color, int offy) {
		return createWatermarkImage(image, watermark, color, Fonts.SIMSUN_BOLD_16PT, offy);
	}
	
	public static BufferedImage createWatermarkImage(BufferedImage image, String watermark, Color color, Font font, int offy) {
		BufferedImage compatibleImage = toCompatibleImage(image);
		
		int width = compatibleImage.getWidth();
		int height = compatibleImage.getHeight();
		
		Graphics2D g2d = (Graphics2D) compatibleImage.getGraphics();
		g2d.setFont(font);
		
		FontMetrics fontMetrics = g2d.getFontMetrics();
		int tWidth = fontMetrics.charsWidth(watermark.toCharArray(), 0, watermark.length());
		int tHeight = fontMetrics.getHeight() * offy;
		
		int x = (width - tWidth) / 2;
		int y = (height) / 2 + tHeight;
		
		g2d.setColor(color);
		RenderingUtils.drawString(g2d, watermark, x, y);
		return compatibleImage;
	}
	
	public static BufferedImage loadCompatibleImage(URL resource) throws IOException {
		BufferedImage image = ImageIO.read(resource);
		return toCompatibleImage(image);
	}
	
	public static void writeImage(BufferedImage image, String formatName, String path, String filename) throws IOException {
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();
		}
		ImageIO.write(image, formatName, new File(path + File.separator + filename));
	}
	
	public static BufferedImage toCompatibleImage(BufferedImage image) {
		if (GraphicsEnvironment.isHeadless()) {
			return image;
		}
		
		if (image.getColorModel().equals(getGraphicsConfiguration().getColorModel())) {
			return image;
		}
		
		BufferedImage compatibleImage = createCompatibleImage(image);
		Graphics g = compatibleImage.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		
		return compatibleImage;
	}
}

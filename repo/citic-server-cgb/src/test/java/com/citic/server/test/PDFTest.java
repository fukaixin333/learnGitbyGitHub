package com.citic.server.test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.citic.server.runtime.Utility;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.Fonts;
import com.citic.server.utils.GraphicsUtilities;
import com.citic.server.utils.PDFUtils;

public class PDFTest {
	
	@SuppressWarnings("unchecked")
	public void pdfTest() throws Exception {
		String bdhm = "A220170710110101200001";
		
		BufferedImage srcImage = ImageIO.read(new File("E:\\pdftest\\touming.png"));
		BufferedImage watermarkImage = GraphicsUtilities.createWatermarkImage(srcImage, Utility.currDateCN(), Color.RED);
		watermarkImage = GraphicsUtilities.createWatermarkImage(watermarkImage, Utility.currDateTime19(), Color.BLACK, Fonts.SIMSUN_BOLD_16PT, 1);
		String watermarkImagePath = bdhm + Utility.currDateTime14() + ".png";
		ImageIO.write(watermarkImage, "PNG", new File("E:\\pdftest\\" + watermarkImagePath));
		
		String pdfFileName = bdhm + Utility.currDateTime14() + ".pdf";
		
		byte[] byts = CommonUtils.readBinaryFile("E:\\pdftest\\" + bdhm + ".bt");
		ByteArrayInputStream bais = new ByteArrayInputStream(byts);
		ObjectInputStream objIn = new ObjectInputStream(bais);
		Map<String, Object> record = (Map<String, Object>) objIn.readObject();
		record.put("sealpath", watermarkImagePath);
		
		PDFUtils.html2pdf("gf/hzws_dj.html", "E:\\pdftest", pdfFileName, record, "file:/E:\\pdftest/");
	}
	
	public static void a() throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "ssss");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(map);
		byte[] s = baos.toByteArray();
		CommonUtils.writeBinaryFile(s, "E:\\pdftest\\", "1.ss");
	}
}

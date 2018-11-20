package org.spa.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
/**
 * Created by Ivy on 2016/01/16.
 */
public class QRCodeUtil {
	public static String generateQRCodeInBase64(String message, int size) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Map hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.MARGIN, 1);

		BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size, hints);
			MatrixToImageWriter.writeToStream(bitMatrix, "png", os);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] encodedImage = Base64.getEncoder().encode(os.toByteArray());

		String str = new String(encodedImage);

		return str;
	}
}

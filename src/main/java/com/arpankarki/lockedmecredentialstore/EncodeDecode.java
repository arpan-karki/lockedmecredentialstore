package com.arpankarki.lockedmecredentialstore;

import java.nio.charset.StandardCharsets;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;

public class EncodeDecode {

	final static Logger encodeLogger = Logger.getLogger(EncodeDecode.class);

	public static String encode(String str) {
		encodeLogger.info("Encoding ");
		return DatatypeConverter.printBase64Binary(str.getBytes());
	}

	public static String decode(String str) {
		encodeLogger.info("Decoding ");
		return new String(DatatypeConverter.parseBase64Binary(str), StandardCharsets.UTF_8);

	}
}

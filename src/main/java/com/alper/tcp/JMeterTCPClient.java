package com.alper.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.jmeter.protocol.tcp.sampler.TCPClient;
import org.apache.jmeter.util.JMeterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class used to test the functionality of the TCPSampler in JMeter. Build this
 * jar and add it to the class path of your JMeter startup. Follow documentation
 * <a href=
 * "http://jmeter.apache.org/usermanual/component_reference.html#TCP_Sampler"
 * >here</a> on how to set up a sampler.
 * 
 * @author Alper Akture
 * 
 */
public class JMeterTCPClient implements TCPClient {
	private static final Logger log = LoggerFactory.getLogger(JMeterTCPClient.class);
	private byte eolByte = (byte) JMeterUtils.getPropDefault("tcp.eolByte", 0);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.jmeter.protocol.tcp.sampler.TCPClient#setupTest()
	 */
	public void setupTest() {
		log.info("setuptest");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.jmeter.protocol.tcp.sampler.TCPClient#teardownTest()
	 */
	public void teardownTest() {
		log.info("teardowntest");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.jmeter.protocol.tcp.sampler.TCPClient#write(java.io.OutputStream
	 * , java.lang.String)
	 */
	public void write(OutputStream os, String s) {
		
		PrintWriter writer = new PrintWriter(os);
		writer.println(s);
		writer.flush();
		//writer.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.jmeter.protocol.tcp.sampler.TCPClient#read(java.io.InputStream
	 * )
	 */
	public String read(InputStream is) {
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(is));
		String modifiedSentence = null;
		try {
			modifiedSentence = inFromServer.readLine();
			log.info("got from server: {}", modifiedSentence);
			is.close();
		} catch (IOException e) {
			log.error("error", e);
		}
		return modifiedSentence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.jmeter.protocol.tcp.sampler.TCPClient#write(java.io.OutputStream
	 * , java.io.InputStream)
	 */
	public void write(OutputStream os, InputStream is) {
		// TODO Auto-generated method stub
		return;
	}

	/**
	 * @return Returns the eolByte.
	 */
	public byte getEolByte() {
		return eolByte;
	}

	/**
	 * @param eolByte
	 *            The eolByte to set.
	 */
	public void setEolByte(byte eolByte) {
		this.eolByte = eolByte;
	}

	public void setEolByte(int arg0) {
		// TODO Auto-generated method stub

	}
}

package com.alper.tcp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alper.tcp.JMeterTCPClient;

/**
 * You need to start the ToUpperServer.java class before running these test. It can 
 * be found in the project tcpserver. I have been running it directly from inside eclipse.
 * 
 * @author Alper Akture
 *
 */
public class JMeterTCPSamplerTest {
private static final Logger log = LoggerFactory.getLogger(JMeterTCPSamplerTest.class);
	@Test
	@Ignore
	public void testServerDirectly() throws Exception {
		Socket clientSocket = new Socket("localhost", 6789);
		PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
		writer.println("hello");
		writer.flush();
		
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		String modifiedSentence = inFromServer.readLine();
		log.info("from server: {}", modifiedSentence);
		writer.close();
		clientSocket.close();
		Assert.assertEquals("HELLO", modifiedSentence);
	}
	
	@Test
	@Ignore
	public void testJMeterClient() throws Exception {
		Socket clientSocket = new Socket("localhost", 6789);
		InputStream is = clientSocket.getInputStream();
		JMeterTCPClient client = new JMeterTCPClient();
		log.info("write hello to server");
		client.write(clientSocket.getOutputStream(), "hello");
		String response = client.read(is);
		Assert.assertEquals("HELLO", response);
		log.info("response in test is: {}", response);
	}

}

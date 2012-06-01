package com.alper.commons.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonDeserializerTest {
	private static final Logger log = LoggerFactory.getLogger(JsonDeserializerTest.class);

	@Test
	public void testJsonDeserialization() throws Exception {
		String jsonInput = readFileAsString("sample.json");
		log.info("json input: {}", jsonInput);
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = mapper.readValue(new File(this.getClass().getClassLoader().getResource("json/sample.json").getPath()), Shop.class);
		log.info("shop toString() {}", shop);
		log.info("object as string: {}", mapper.writeValueAsString(shop));
	}

	/**
	 * @param filePath
	 *            name of file to open. The file can reside anywhere in the
	 *            classpath
	 */
	private String readFileAsString(String filename) throws java.io.IOException {
		URL fileUrl = this.getClass().getClassLoader().getResource("json/sample.json");
		log.info("url is {}", fileUrl.getPath());
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass()
				.getClassLoader().getResourceAsStream("json/sample.json")));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}
}

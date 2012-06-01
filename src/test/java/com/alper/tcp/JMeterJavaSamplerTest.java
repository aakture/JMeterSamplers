package com.alper.tcp;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.alper.java.JMeterJavaSampler;

/**
 * Just calls the JMeterJavaSampler directly to test that it basically works. You 
 * do not need the ToUpperServer running.
 * 
 * @author Alper Akture
 *
 */
public class JMeterJavaSamplerTest {
	
	@Test
	@Ignore
	public void test() throws Exception {
		Arguments args = new Arguments();
		args.addArgument("username", "alper");
		args.addArgument("password", "password123");
		args.addArgument("text", "this should be upper case");
		JMeterJavaSampler sampler = new JMeterJavaSampler();
		SampleResult sampleResult = sampler.runTest(new JavaSamplerContext(args));
		Assert.assertTrue(sampleResult.isSuccessful());
		
	}
}

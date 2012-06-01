package com.alper.json;

import static org.junit.Assert.assertTrue;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.junit.Test;

public class JMeterJsonLoginSamplerTest {

	@Test
	public void test() throws Exception {
		Arguments args = new Arguments();
		args.addArgument("username", "alper");
		args.addArgument("password", "alper");
		JMeterJsonLoginSampler sampler = new JMeterJsonLoginSampler();
		SampleResult sampleResult = sampler.runTest(new JavaSamplerContext(args));
		assertTrue(sampleResult.isSuccessful());
	}

}

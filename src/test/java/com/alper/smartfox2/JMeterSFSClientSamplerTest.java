package com.alper.smartfox2;

import junit.framework.Assert;

import org.apache.jmeter.samplers.SampleResult;
import org.junit.Ignore;
import org.junit.Test;

/**
 * NOTE: You need to start SmartFoxServer 2x before running this test.
 * 
 * @author aakture
 * 
 */
public class JMeterSFSClientSamplerTest {

	/**
	 * Does a simple runTest that does a login to SmartFox. Passing a null
	 * JavaSamplerContext in runTest, since this is provided by JMeter, and we
	 * don't necessarily need it for a test. This test needs the SmartFoxServer2X running
	 * for it to pass.
	 */
	@Test
	@Ignore("remove ignore if you have SmartFox running")
	public void test() {
		JMeterSFSClientSampler sampler = new JMeterSFSClientSampler();
		SampleResult sampleResult = sampler.runTest(null);
		Assert.assertTrue(sampleResult.isSuccessful());
	}

}

package com.alper.java;

import java.util.Iterator;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alper.tcpserver.ToUpperClient;

/**
 * A class to test JMeter Java Request extension. You need to build the project
 * and then drop the jar in the lib/ext of your JMeter install for it to show up
 * in the Java Request panel drop down list.
 *
 * @author Alper Akture
 * 
 */
public class JMeterJavaSampler extends AbstractJavaSamplerClient {
	private static final Logger log = LoggerFactory.getLogger(JMeterJavaSampler.class);

	@Override
	public void setupTest(JavaSamplerContext context) {
		log.info("in setupTest()");
		listParameters("setupTest()", context);
	}

    @Override
    public void teardownTest(JavaSamplerContext context) {
    	log.info("in teardownTest()");
        listParameters("teardownTest", context);
    }
    /**
     * Called by JMeter during test run.
     */
	public SampleResult runTest(JavaSamplerContext context) {
		SampleResult sampleResult = new SampleResult();
		sampleResult.sampleStart();
		long sleepTime = Math.round(2000d * Math.random());
		try {
			log.info("sleeping for {} ms to simulate some load", sleepTime);
			Thread.sleep(sleepTime);
		} catch (Exception ex) {
			log.error("error", ex);
		}
		String username = context.getParameter("username");
		String password = context.getParameter("password");
		String textFromJMeter = context.getParameter("text");
		log.info("from context, got username: {}, and password: {}, and text: {}", new Object[] {
				username, password, textFromJMeter });
		if (ToUpperClient.toUpper(textFromJMeter).equals(textFromJMeter.toUpperCase())) {
			sampleResult.setSuccessful(true);
		} else {
			sampleResult.setSuccessful(false);
		}
		sampleResult.sampleEnd();
		log.info("sample time: {}", sampleResult.getTime());
		return sampleResult;
	}

	@Override
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("username", "");
		params.addArgument("password", "");
		params.addArgument("text", "enter text to uppercase");
		return params;
	}

	/**
	 * Dump a list of the parameters in this context to the debug log.
	 * 
	 * @param context
	 *            the context which contains the initialization parameters.
	 */
	private void listParameters(String methodName, JavaSamplerContext context) {

		Iterator<String> iter = context.getParameterNamesIterator();
		while (iter.hasNext()) {
			String name = iter.next();
			log.debug("inside {}, name {} = {}", new Object[]{methodName, name, context.getParameter(name)});
		}

	}
}

package com.alper.smartfox2;

import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.requests.LoginRequest;

import com.smartfoxserver.v2.exceptions.SFSException;

/**
 * This is a JMeter Java Sample class that is used test that we can connect to SmartFoxServer and 
 * do some basic login call. This will be called by JMeter. 
 * <p>
 * To run, you need to build this project and then copy the jar file to the JMeter/lib/ext.
 * <p>
 * You also need to place these SFS dependencies in JMeter/lib/ext (refer to pom for any that may be 
 * missing that should have been listed here):
 * <ul>
 * <li>SFS2X_API_Java.jar</li>
 * <li>sfs2x-client-core.jar</li>
 * <li>netty-3.2.2.Final.jar</li>
 * </ul>
 * 
 * <p>
 * @author aakture
 *
 */
public class JMeterSFSClientSampler extends AbstractJavaSamplerClient {
	private static final Logger log = LoggerFactory.getLogger(JMeterSFSClientSampler.class);

	private boolean success = false;
	private CountDownLatch latch = new CountDownLatch(2);
	private String username;
	private String password;
	
	public SampleResult runTest(JavaSamplerContext context) {
		setJMeterContextParams(context);
		SampleResult sampleResult = new SampleResult();
		sampleResult.sampleStart();
		SmartFox sfs = new SmartFox(true);
		//URL configFileUrl = this.getClass().getClassLoader().getResource("/Users/aakture/Documents/workspace-sts-2.9.1.RELEASE/tcp_test/Sfs2xJMeterClientSampler/src/test/resources/test-config.xml");
		URL configFileURL = this.getClass().getResource("/config.xml");
		if(configFileURL == null) {
			log.error("error loading SmartFox config.xml file");
			throw new RuntimeException("SmartFoxServer config.xml could not be found. Make sure it's available in the classpath.");
		}
		//File file = new File(configFileURL.getPath());
		sfs.loadConfig(configFileURL.getPath());
		LoginEventHandler handler = new LoginEventHandler();
		sfs.addEventListener(SFSEvent.LOGIN, handler);
		handler.run();
		
		LoginRequest loginRequest = new LoginRequest("", "", "BasicExamples");
		sfs.send(loginRequest);
		synchronized(latch) {
			try {
				log.info("wait for event in runTest()");
				latch.await(500, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				log.error("error", e);
			}
		}
		sampleResult.setSuccessful(success);
		sampleResult.sampleEnd();
		log.info("sample time: {}", sampleResult.getTime());
		return sampleResult;
	}

	private void setJMeterContextParams(JavaSamplerContext context) {
		if(context != null) {
			username = context.getParameter("username");
			log.info("set username from context to {}", username);
			password = context.getParameter("password");
			log.info("set password from context to {}", password);
		} else {
			log.info("context is null, not setting any parameters.");
		}
		
	}

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

	@Override
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("username", "");
		params.addArgument("password", "");
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
			log.debug("inside {}, name {} = {}",
					new Object[] { methodName, name, context.getParameter(name) });
		}
	}

	class LoginEventHandler implements IEventListener, Runnable {
		
		public void run() {
			log.info("waiting for login event in LoginEventHandler...");
			synchronized(latch) {
				try {
					log.info("inside synch block");
					latch.await(500, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					log.error("error", e);
				}
			}
		}

		public void dispatch(BaseEvent baseEvent) throws SFSException {
			log.info("received event: {}", baseEvent.getType());
			success = true;
			synchronized(latch) {
				latch.countDown();
			}
			log.info("finished");
		}
		
	}

}

package com.autentia.tutoriales.spring.retry;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.autentia.tutoriales.spring.retry.service.ExampleService;
import com.autentia.tutoriales.spring.retry.service.impl.ExampleServiceImpl;

public class RetryTemplateTest {

	private static final Logger LOG = LoggerFactory.getLogger(RetryTemplateTest.class);

	private ExampleService service;

	private RetryTemplate retryTemplate;

	@Before
	public void init() {
		retryTemplate = new RetryTemplate();

		Map<Class<? extends Throwable>, Boolean> supportedExceptionsMap = new HashMap<Class<? extends Throwable>, Boolean>();
		supportedExceptionsMap.put(Exception.class, true);
		
		RetryPolicy retryPolicy = new SimpleRetryPolicy(5, supportedExceptionsMap
				);
		FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
		backOffPolicy.setBackOffPeriod(5000);
		
		retryTemplate.setRetryPolicy(retryPolicy);
		retryTemplate.setBackOffPolicy(backOffPolicy);
		
		service = spy(new ExampleServiceImpl());
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void givenAServiceMethodWhenTryingToInvokeItThenRetry5Times() {

		try {

			LOG.trace("[TEST] Trying to invoke send mail method...");

			retryTemplate.execute(new RetryCallback() {
				public String doWithRetry(RetryContext arg0) throws Exception {
					System.out.println(String.format("\tRetry count ->  %s ",
							arg0.getRetryCount()));
					return service.sendMail();
				}
			});

			verify(service, times(5)).sendMail();
			verifyNoMoreInteractions(service);
			

		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}

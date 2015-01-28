package com.autentia.tutoriales.spring.retry.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.autentia.tutoriales.spring.retry.service.ExampleService;

public class Main {

	private static RetryTemplate retryTemplate = new RetryTemplate();
	
	{
		Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<Class<? extends Throwable>, Boolean>();
		retryableExceptions.put(IOException.class, true);

		ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
		backOffPolicy.setInitialInterval(500);
		backOffPolicy.setMaxInterval(20000);
		backOffPolicy.setMultiplier(1.5);

		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(10, retryableExceptions);
		retryPolicy.setMaxAttempts(10);

		retryTemplate.setRetryPolicy(retryPolicy);
		retryTemplate.setBackOffPolicy(backOffPolicy);
	}
	
	public static void main(String[] args) {

		
	}
}

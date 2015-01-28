package com.autentia.tutoriales.spring.retry.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.autentia.tutoriales.spring.retry.service.ExampleService;

@Service(value = "exampleService")
public class ExampleServiceImpl implements ExampleService {

	private static final Logger LOG = LoggerFactory
			.getLogger(ExampleServiceImpl.class);

	private int times = 0;

	public String sendMail() throws Exception {

		LOG.trace("Sending mail");

		if (times < 4) {
			times++;
			throw new Exception("Retrying mail sending...");
		}

		LOG.trace("Mail sent!");

		return "OK";
	}

	public int getTimes() {
		return times;
	}

}

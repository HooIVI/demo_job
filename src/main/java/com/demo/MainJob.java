package com.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class MainJob {

	public static void main(final String[] args) {
		new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	}

}

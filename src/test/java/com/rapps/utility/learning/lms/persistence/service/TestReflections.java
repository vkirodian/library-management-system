package com.rapps.utility.learning.lms.persistence.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.rapps.utility.learning.lms.controller.IAuthenticationMgmt;

public class TestReflections {

	public static void main(String[] args) {

		Method[] methods = IAuthenticationMgmt.class.getMethods();

		for (Method m : methods) {
			System.out.println(m.getName());
			Annotation[] annotations = m.getAnnotations();
			for (Annotation a : annotations) {
				System.out.println(a);
			}
			System.out.println("-------");
		}
	}
}

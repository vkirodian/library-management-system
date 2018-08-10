package com.rapps.utility.learning.lms.helper;

public class TestClassWithOneInterfaces implements TestInterface {

	public void notInInterfaceMethod() {
	}

	@SuppressWarnings("unused")
	private void privateMethod() {
	}

	@Override
	public void methodWithoutAuth() {
	}

	@Override
	public void methodWithAllowAllAuth() {
	}

	@Override
	public void methodWithAuth() {
	}

}

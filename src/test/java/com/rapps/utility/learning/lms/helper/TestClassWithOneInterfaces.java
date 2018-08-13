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
	public void methodWithAuth() {
	}

	@Override
	public void methodSkipSession() {
	}

}

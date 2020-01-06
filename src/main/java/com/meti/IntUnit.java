package com.meti;

public class IntUnit implements Unit {
	@Override
	public boolean canCompile(String value) {
		boolean isInt;
		try {
			Integer.parseInt(value);
			isInt = true;
		} catch (Exception e) {
			isInt = false;
		}
		return isInt;
	}

	@Override
	public String compile(String trim, Compiler compiler) {
		return trim;
	}
}

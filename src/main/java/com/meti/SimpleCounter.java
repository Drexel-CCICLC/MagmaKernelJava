package com.meti;

public class SimpleCounter implements Counter {
	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	private int counter = -1;

	@Override
	public String next() {
		counter++;
		return ALPHABET.charAt(counter % ALPHABET.length()) + String.valueOf(counter);
	}
}
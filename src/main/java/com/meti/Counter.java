package com.meti;

public class Counter {
	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	int counter = -1;

	public Counter() {
	}

	public String next() {
		counter++;
		return ALPHABET.charAt(counter % ALPHABET.length()) + String.valueOf(counter);
	}
}
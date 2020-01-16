package com.meti.struct;

public class IncrementedGenerator implements Generator {
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	private int counter = -1;

	@Override
	public String next() {
		counter++;
		String index = String.valueOf(ALPHABET.indexOf(counter % ALPHABET.length()));
		String value = String.valueOf(counter);
		return index + value;
	}
}

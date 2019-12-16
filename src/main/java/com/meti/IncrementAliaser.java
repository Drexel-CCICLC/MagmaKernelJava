package com.meti;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IncrementAliaser implements Aliaser {
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	private final Map<String[], String> aliases = new HashMap<>();
	private int counter = -1;

	@Override
	public String alias(String... names) {
		for (Map.Entry<String[], String> entry : aliases.entrySet()) {
			if (Arrays.deepEquals(entry.getKey(), names)) {
				return entry.getValue();
			}
		}
		counter++;
		String sum = ALPHABET.charAt(counter % ALPHABET.length()) + String.valueOf(counter);
		aliases.put(names, sum);
		return sum;
	}
}

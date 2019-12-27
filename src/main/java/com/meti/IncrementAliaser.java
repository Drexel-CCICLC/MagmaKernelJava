package com.meti;

import java.util.HashMap;
import java.util.Map;

public class IncrementAliaser implements Aliaser {
	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	private final Map<String, String> aliases = new HashMap<>();
	private int counter = -1;

	@Override
	public String alias(String name) {
		if (!aliases.containsKey(name)) {
			counter++;
			aliases.put(name, ALPHABET.charAt(counter % ALPHABET.length()) + String.valueOf(counter));
		}
		return aliases.get(name);
	}
}
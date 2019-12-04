package com.meti.compile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class SimpleAliaser implements Aliaser {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private final Map<String[], String> aliases = new HashMap<>();
    private int counter = -1;

    private String next() {
        counter++;
        String letterPart = String.valueOf(ALPHABET.charAt(counter % ALPHABET.length()));
        String intPart = String.valueOf(counter);
        return letterPart + intPart;
    }

    @Override
    public String alias(String... names) {
        Optional<String> alias = aliases.keySet()
                .stream()
                .filter(strings -> Arrays.deepEquals(strings, names))
                .map(aliases::get)
                .findAny();
        if (alias.isPresent()) return alias.get();
        String value = next();
        aliases.put(names, value);
        return value;
    }
}

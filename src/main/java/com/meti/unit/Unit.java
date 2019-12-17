package com.meti.unit;

import com.meti.Compiler;

import java.util.Optional;

public interface Unit {
	Optional<String> parse(String input, Compiler compiler);
}

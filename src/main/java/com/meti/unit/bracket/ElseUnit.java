package com.meti.unit.bracket;

import com.meti.Compiler;
import com.meti.type.Type;
import com.meti.unit.Unit;

import java.util.Optional;

public class ElseUnit implements Unit {
	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		if (input.startsWith("else")) {
			String content = compiler.compile(input.substring(4));
			return Optional.of("else" + content);
		}
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolve(String input, Compiler compiler) {
		return Optional.empty();
	}
}

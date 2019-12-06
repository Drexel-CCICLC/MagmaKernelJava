package com.meti.interpret;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VoidType implements Type {
	@Override
	public Optional<String> value() {
		return Optional.of("void");
	}

	@Override
	public List<Type> parameters() {
		return new ArrayList<>();
	}

	@Override
	public Optional<Type> returnType() {
		return Optional.empty();
	}
}

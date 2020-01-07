package com.meti;

import java.util.Optional;

public class VariableUnit implements Unit {
	private final Declarations declarations;

	public VariableUnit(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public boolean canCompile(String value) {
		return true;
	}

	@Override
	public String compile(String value, Compiler compiler) {
		Optional<Declaration> optional = declarations.relative(value);
		if (optional.isPresent()) {
			return optional.get().name();
		} else {
			throw new DoesNotExistException(value + " is not defined.");
		}
	}

	@Override
	public Optional<Type> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		Optional<Declaration> optional = declarations.relative(value);
		if (optional.isPresent()) {
			return optional.map(Declaration::type).map(Type::new);
		} else {
			throw new DoesNotExistException(value + " is not defined.");
		}
	}
}

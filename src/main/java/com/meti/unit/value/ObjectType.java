package com.meti.unit.value;

import com.meti.type.Type;
import com.meti.unit.Declaration;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class ObjectType implements RecursiveType {
	private final Declaration parent;

	public ObjectType(Declaration parent) {
		this.parent = parent;
	}

	@Override
	public Declaration parentDeclaration() {
		return parent;
	}

	@Override
	public Collection<Type> children(){
		return Collections.emptySet();
	}

	@Override
	public Optional<Type> parent() {
		return Optional.empty();
	}

	@Override
	public Optional<Type> returnType() {
		return Optional.empty();
	}
}

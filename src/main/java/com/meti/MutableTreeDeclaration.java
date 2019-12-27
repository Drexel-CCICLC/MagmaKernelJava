package com.meti;

import com.meti.type.PrimitiveType;
import com.meti.type.Type;
import com.meti.unit.Declaration;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

final class MutableTreeDeclaration extends TreeDeclaration implements MutableDeclaration {

	MutableTreeDeclaration(Type type, String name) {
		this(Collections.emptyList(), type, name);
	}

	MutableTreeDeclaration(Collection<String> flags, Type type, String name) {
		this(flags, type, new LinkedHashMap<>(), name);
	}

	private MutableTreeDeclaration(Collection<String> flags, Type type, Map<String, Declaration> children,
	                               String name) {
		super(children, flags, name, type);
	}

	MutableTreeDeclaration(Collection<String> flags, String name) {
		this(flags, PrimitiveType.ANY, name);
	}

	public MutableTreeDeclaration(Map<String, Declaration> children,
	                              String name) {
		this(Collections.emptyList(), PrimitiveType.ANY, children, name);
	}

	@Override
	public void setType(Type type) {
		this.type = type;
	}
}

package com.meti;

import java.util.Map;

public class GenericStructImpl extends ObjectStruct implements GenericStruct {
	private final Map<String, Struct> structs;

	@Override
	public Map<String, Struct> generics() {
		return structs;
	}

	public GenericStructImpl(String name, Map<String, Struct> structs) {
		this(name, null, structs);
	}

	public GenericStructImpl(String name, Node parent, Map<String, Struct> structs) {
		super(name, parent);
		this.structs = structs;
	}
}

package com.meti.unit;

import com.meti.type.Type;

import java.util.List;
import java.util.Map;

public interface Declaration {
	Map<String, Declaration> getChildren();

	List<String> getFlags();

	Type getType();
}

package com.meti;

import com.meti.type.Type;

public interface Compiler {
	String compile(String input);

	Type resolve(String value);
}

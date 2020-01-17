package com.meti.node;

import com.meti.compile.Compiler;

import java.util.Collection;

public interface Parser {
	Collection<Node> parseMultiple(String value, Compiler compiler);
}

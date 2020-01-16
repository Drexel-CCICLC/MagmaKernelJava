package com.meti;

import java.util.Collection;

public interface Parser {
	Collection<Node> parseMultiple(String value, Compiler compiler);
}

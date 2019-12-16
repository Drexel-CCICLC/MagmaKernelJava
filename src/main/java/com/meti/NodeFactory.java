package com.meti;

import java.util.Optional;

public interface NodeFactory {
	Optional<Node> parse(String value, Parser parser);

	Optional<Struct> parse(String value);
}

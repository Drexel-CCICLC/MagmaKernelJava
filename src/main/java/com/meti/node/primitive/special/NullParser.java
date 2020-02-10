package com.meti.node.primitive.special;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class NullParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		return Optional.of(content)
				.map(String::trim)
				.filter("null"::equals)
				.map(s -> NullNode.INSTANCE);
	}
}

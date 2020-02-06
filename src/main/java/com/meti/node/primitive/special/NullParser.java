package com.meti.node.primitive.special;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class NullParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.equals("null")) {
			return Optional.of(new NullNode());
		}
		return Optional.empty();
	}
}

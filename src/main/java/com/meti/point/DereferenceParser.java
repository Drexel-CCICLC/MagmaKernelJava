package com.meti.point;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;

import java.util.Optional;

public class DereferenceParser implements Parser {
	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.startsWith("*")) {
			String dereferenceValue = trim.substring(1);
			Node child = compiler.compile(dereferenceValue);
			return Optional.of(new DereferenceNode(child));
		}
		return Optional.empty();
	}
}

package com.meti.node.bracket.declare;

import com.meti.compile.Compiler;
import com.meti.declare.Declarations;
import com.meti.node.EmptyNode;
import com.meti.node.Node;
import com.meti.node.Parser;
import com.meti.node.Type;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeclareParser implements Parser {
	private final Declarations declarations;

	public DeclareParser(Declarations declarations) {
		this.declarations = declarations;
	}

	private Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.contains("=")) {
			int equals = trim.indexOf('=');
			String first = trim.substring(0, equals).trim();
			String last = trim.substring(equals + 1).trim();
			int lastSpace = first.lastIndexOf(' ');
			if (-1 == lastSpace) {
				return Optional.empty();
			} else {
				String flagString = first.substring(0, lastSpace);
				List<Flag> flags = Arrays.stream(flagString.split(" "))
						.map(String::toUpperCase)
						.map(Flag::valueOf)
						.collect(Collectors.toList());
				String name = first.substring(lastSpace + 1);
				if (flags.contains(Flag.VAL) || flags.contains(Flag.VAR)) {
					Type type = compiler.resolveValue(last);
					if (flags.contains(Flag.NATIVE)) {
						declarations.define(name, type, false);
						return Optional.of(new EmptyNode());
					} else {
						Node valueNode = declarations.define(name, type, () -> compiler.parseSingle(last));
						return Optional.of(new DeclareNode(type, name, valueNode));
					}
				}
			}
        }
		return Optional.empty();
	}

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return parse(value, compiler).stream().collect(Collectors.toSet());
	}
}

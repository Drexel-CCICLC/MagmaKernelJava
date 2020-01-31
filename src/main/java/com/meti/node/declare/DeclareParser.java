package com.meti.node.declare;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.Type;

import java.util.Arrays;
import java.util.Optional;

public class DeclareParser implements Parser {
	private final Declarations declarations;

	public DeclareParser(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Node> parse(String content, Compiler compiler) throws ParseException {
		if (content.contains("=")) {
			int equalsIndex = content.indexOf('=');
			String beforeEquals = content.substring(0, equalsIndex).trim();
			String afterEquals = content.substring(equalsIndex + 1).trim();
			int lastSpace = beforeEquals.lastIndexOf(' ');
			String flagString = "";
			String nameString = beforeEquals;
			if (-1 != lastSpace) {
				flagString = beforeEquals.substring(0, lastSpace);
				nameString = beforeEquals.substring(lastSpace + 1);
			}
			boolean hasDeclareFlag = Arrays.stream(flagString.split(" "))
					.anyMatch(s -> s.equals("val") || s.equals("var"));
			if (hasDeclareFlag) {
				Node declaration = declarations.inStack(nameString, s -> {
					try {
						Type type = compiler.resolveValue(afterEquals);
						declarations.defineParent(type, s);
						return new DeclareNode(type, s, compiler.parse(afterEquals));
					} catch (ParseException e) {
						throw new RuntimeException(e);
					}
				});
				return Optional.of(declaration);
			} else {
				Node from = compiler.parse(afterEquals);
				Node to = compiler.parse(nameString);
				return Optional.of(new AssignNode(to, from));
			}
		}
		return Optional.empty();
	}
}

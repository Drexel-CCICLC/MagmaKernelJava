package com.meti;

import com.meti.exception.ParseException;

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
			Type type = compiler.resolveValue(afterEquals);
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
				Node from = declarations.define(type, nameString, () -> {
					try {
						return compiler.parse(afterEquals);
					} catch (ParseException e) {
						throw new RuntimeException(e);
					}
				});
				return from.render().isBlank() ?
						Optional.of(new EmptyNode()) :
						Optional.of(new DeclareNode(type, nameString, from));
			} else {
				Node from = compiler.parse(afterEquals);
				Node to = compiler.parse(nameString);
				return Optional.of(new AssignNode(to, from));
			}
		}
		return Optional.empty();
	}
}

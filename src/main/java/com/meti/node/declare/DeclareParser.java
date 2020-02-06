package com.meti.node.declare;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.exception.ParseException;
import com.meti.exception.StateException;
import com.meti.node.Node;
import com.meti.node.Type;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DeclareParser implements Parser {
	private final Declarations declarations;

	public DeclareParser(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.contains("=")) {
			int equalsIndex = trim.indexOf('=');
			if (!isValid(trim, equalsIndex)) {
				return Optional.empty();
			}
			String beforeEquals = trim.substring(0, equalsIndex).trim();
			String afterEquals = trim.substring(equalsIndex + 1).trim();
			int lastSpace = beforeEquals.lastIndexOf(' ');
			String flagString = "";
			String nameString = beforeEquals;
			if (-1 != lastSpace) {
				flagString = beforeEquals.substring(0, lastSpace);
				nameString = beforeEquals.substring(lastSpace + 1);
			}
			Set<Flag> flags = Arrays.stream(flagString.split(" "))
					.filter(s -> !s.isBlank())
					.map(String::toUpperCase)
					.map(Flag::valueOf)
					.collect(Collectors.toSet());
			boolean hasDeclareFlag = flags.contains(Flag.VAR) || flags.contains(Flag.VAL);
			if (flags.contains(Flag.SINGLE)) {
				nameString += "$";
			}
			if (hasDeclareFlag) {
				if (declarations.relative(nameString).isPresent()) {
					throw new StateException(nameString + " is already defined.");
				}
				Node declaration = declarations.inStack(nameString, name -> {
					try {
						Set<Flag> parentFlags = declarations.flags();
						Set<Flag> parentFlagCopy = EnumSet.copyOf(parentFlags);
						parentFlags.clear();
						parentFlags.addAll(flags);
						Type type = compiler.resolveValue(afterEquals);
						declarations.defineParent(type, name, flags);
						Node node = compiler.parse(afterEquals);
						parentFlags.removeAll(flags);
						parentFlags.addAll(parentFlagCopy);
						return new DeclareNode(type, name, node);
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

	private boolean isValid(String trim, int equalsIndex) {
		String twoString = trim.substring(equalsIndex, equalsIndex + 2);
		if (twoString.equals("==")) {
			return trim.substring(equalsIndex, equalsIndex + 3).equals("==>");
		} else return !trim.substring(equalsIndex - 1, equalsIndex + 1).equals("!=");
	}
}

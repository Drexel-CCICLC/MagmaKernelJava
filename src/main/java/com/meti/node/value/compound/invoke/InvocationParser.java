package com.meti.node.value.compound.invoke;

import com.meti.compile.Compiler;
import com.meti.declare.Declaration;
import com.meti.declare.Declarations;
import com.meti.node.Node;
import com.meti.node.Parser;
import com.meti.node.Type;

import java.util.*;
import java.util.stream.Collectors;

public class InvocationParser implements Parser {
	private final Declarations declarations;

	public InvocationParser(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return parse(value, compiler).stream().collect(Collectors.toSet());
	}

	private Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.contains("(") && trim.endsWith(")")) {
			int start = trim.indexOf('(');
			int end = trim.indexOf(')');
			String callerString = trim.substring(0, start).trim();
			String argumentString = trim.substring(start + 1, end);
			Type callerType = compiler.resolveValue(callerString);
			Node caller = compiler.parseSingle(callerString);
			String[] args = argumentString.split(",");
			List<Node> arguments = Arrays.stream(args)
					.filter(arg -> !arg.isBlank())
					.map(compiler::parseSingle).collect(Collectors.toList());
			//TODO: pass method as functional parameter using anonymous functions
			Optional<Declaration> declaration = declarations.relative(callerString);
			if (declaration.isPresent()) {
				List<Declaration> ancestors = new ArrayList<>();
				Optional<Declaration> current = declaration;
				while (current.isPresent()) {
					Declaration actual = current.get();
					ancestors.add(actual);
					current = actual.parent();
				}
				for (Declaration declaration1 : ancestors.subList(1, ancestors.size() - 1)) {
                    Node variableNode = declaration1.toInstance();
					arguments.add(variableNode);
				}
			}
			return Optional.of(new InvocationNode(caller, arguments, callerType.doesReturnVoid()));
		}
		return Optional.empty();
	}

}

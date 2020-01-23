package com.meti.node.value.compound.invoke;

import com.meti.compile.Compiler;
import com.meti.declare.Declarations;
import com.meti.node.Node;
import com.meti.node.Parser;
import com.meti.node.StructType;
import com.meti.node.Type;
import com.meti.node.other.VoidType;
import com.meti.node.value.compound.variable.VariableNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvocationParser implements Parser {
	private final Declarations declarations;

	public InvocationParser(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.contains("(") && trim.endsWith(")")) {
			int start = trim.indexOf('(');
			int end = trim.indexOf(')');
			String callerString = trim.substring(0, start).trim();
			String argumentString = trim.substring(start + 1, end);
			Type resolvedType = compiler.resolveValue(callerString);
			StructType callerType;
			if (resolvedType instanceof StructType) {
				callerType = (StructType) resolvedType;
			} else {
				throw new IllegalArgumentException(resolvedType + " is not a function.");
			}
			Node caller = compiler.parseSingle(callerString);
			String[] args = argumentString.split(",");
			List<Node> arguments = Arrays.stream(args)
					.filter(arg -> !arg.isBlank())
					.map(compiler::parseSingle)
					.collect(Collectors.toCollection(ArrayList::new));
			int periodIndex = callerString.lastIndexOf('.');
			if (-1 != periodIndex) {
				String parentString = callerString.substring(0, periodIndex);
				arguments.add(new VariableNode(parentString));
			}

			boolean returnsVoid = callerType.returnType().map(type -> type.equals(VoidType.INSTANCE)).orElseThrow();
			return returnsVoid ?
					Optional.of(new VoidInvocationNode(caller, arguments)) :
					Optional.of(new InvocationNode(caller, arguments));
		}
		return Optional.empty();
	}

}

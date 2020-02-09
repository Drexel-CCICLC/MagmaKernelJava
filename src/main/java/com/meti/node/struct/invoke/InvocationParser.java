package com.meti.node.struct.invoke;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.exception.RenderException;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.declare.VariableNode;
import com.meti.node.primitive.special.VoidType;
import com.meti.node.struct.type.FunctionType;
import com.meti.node.struct.type.StructType;
import com.meti.parse.Declaration;
import com.meti.parse.Declarations;
import com.meti.util.ArgumentPartitioner;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InvocationParser implements Parser {
	private final Declarations declarations;

	public InvocationParser(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.endsWith(")")) {
			String caller = trim.substring(0, trim.indexOf('('));
			Node callerNode = compiler.parse(caller);
			List<Node> arguments = parseArguments(compiler, trim, caller);
			return buildNode(compiler.resolveValue(caller), callerNode, arguments);
		}
		return Optional.empty();
	}

	private List<Node> parseArguments(Compiler compiler, String trim, String caller) {
		return Stream.of(parseGivenArguments(compiler, trim),
				parseParentArguments(caller),
				parseStackArguments(caller))
				.reduce(Stream::concat)
				.map(stream -> stream.collect(Collectors.toList()))
				.orElse(Collections.emptyList());
	}

	private Optional<Node> buildNode(Type type, Node caller, List<? extends Node> arguments) {
		if (type instanceof FunctionType) {
			Type returnType = ((FunctionType) type).returnType();
			Node node = buildNodeFromReturnType(caller, arguments, returnType);
			return Optional.of(node);
		} else {
			throw new RenderException(type + " is not a function.");
		}
	}

	private Stream<Node> parseGivenArguments(Compiler compiler, String trim) {
		String values = trim.substring(trim.indexOf('(') + 1, trim.length() - 1);
		return new ArgumentPartitioner(values)
				.partition()
				.stream()
				.filter(s -> !s.isBlank())
				.map(compiler::parse);
	}

	private Stream<Node> parseParentArguments(String caller) {
		if (caller.contains(".")) {
			String parent = caller.substring(0, caller.lastIndexOf('.')).trim();
			String child = caller.substring(caller.lastIndexOf('.') + 1);
			return Stream.concat(checkSingleton(parent), checkClass(parent, child));
		}
		return Stream.empty();
	}

	private Stream<Node> parseStackArguments(String caller) {
		return declarations.relative(caller)
				.map(Declaration::toParentParameters)
				.map(Collection::stream)
				.orElse(Stream.empty());
	}

	private Node buildNodeFromReturnType(Node callerNode, List<? extends Node> arguments, Type returnType) {
		return returnType.equals(VoidType.INSTANCE) ?
				new VoidInvocationNode(callerNode, arguments) :
				new InvocationNode(callerNode, arguments);
	}

	private Stream<VariableNode> checkSingleton(String parent) {
		return declarations.relative(parent + "$")
				.flatMap(declaration -> declaration.child(parent))
				.map(declaration -> new VariableNode(parent))
				.stream();
	}

	private Stream<Node> checkClass(String parent, String child) {
		return declarations.relative(parent)
				.map(Declaration::type)
				.filter(StructType.class::isInstance)
				.map(StructType.class::cast)
				.map(s -> s.bind(parent, child))
				.stream();
	}
}

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

	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		return Optional.of(value)
				.map(String::trim)
				.filter(trim -> trim.contains("="))
				.flatMap(trim -> build(trim, compiler));
	}

	private Optional<Node> build(String trim, Compiler compiler) {
		int equals = trim.indexOf('=');
		String nameString = trim.substring(0, equals).trim();
		String contentString = trim.substring(equals + 1).trim();
		int lastSpace = nameString.lastIndexOf(' ');
		return -1 == lastSpace ?
				Optional.empty() :
				build(compiler, nameString, contentString, lastSpace);
	}

	private Optional<Node> build(Compiler compiler, String nameString, String contentString, int lastSpace) {
		String flagString = nameString.substring(0, lastSpace);
		List<Flag> flags = parseFlags(flagString);
		String name = nameString.substring(lastSpace + 1);
		return buildNode(compiler, contentString, flags, name);
	}

	private List<Flag> parseFlags(String flagString) {
		return Arrays.stream(flagString.split(" "))
				.map(String::toUpperCase)
				.map(Flag::valueOf)
				.collect(Collectors.toList());
	}

	private Optional<Node> buildNode(Compiler compiler, String contentString, Collection<Flag> flags, String name) {
		if (flags.contains(Flag.VAL) || flags.contains(Flag.VAR)) {
			Type contentType = compiler.resolveValue(contentString);
			Node node = buildNode(compiler, contentString, flags, name, contentType);
			return Optional.of(node);
		}
		return Optional.empty();
	}

	private Node buildNode(Compiler compiler, String contentString, Collection<Flag> flags, String name,
	                       Type contentType) {
		return flags.contains(Flag.NATIVE) ?
				buildNative(name, contentType) :
				buildNormal(compiler, contentString, name, contentType);
	}

	private Node buildNative(String name, Type type) {
		declarations.define(name, type);
		return new EmptyNode();
	}

	private Node buildNormal(Compiler compiler, String last, String name, Type type) {
		Node valueNode = declarations.define(name, type, () -> compiler.parseSingle(last));
		return new DeclareNode(type, name, valueNode);
	}
}

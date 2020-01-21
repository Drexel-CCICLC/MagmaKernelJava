package com.meti.node.bracket.struct;

import com.meti.compile.Compiler;
import com.meti.declare.Declaration;
import com.meti.declare.Declarations;
import com.meti.declare.Parameter;
import com.meti.node.EmptyNode;
import com.meti.node.Node;
import com.meti.node.Parser;
import com.meti.node.Type;
import com.meti.node.bracket.block.BlockNode;
import com.meti.node.value.primitive.array.Functions;

import java.util.*;
import java.util.stream.Collectors;

public class StructParser implements Parser {
	private final Declarations declarations;
	private final Functions functions;
	private final Generator generator;

	public StructParser(Declarations declarations, Functions functions, Generator generator) {
		this.declarations = declarations;
		this.generator = generator;
		this.functions = functions;
	}

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return parse(value, compiler).stream().collect(Collectors.toSet());
	}

	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		String content = value.trim();
		if (content.startsWith("(")) {
			Node node = build(compiler, content);
			return Optional.of(node);
		}
		return Optional.empty();
	}

	private Node build(Compiler compiler, String content) {
		Set<Parameter> parameters = parseAllParameters(compiler, content);
		Type returnType = resolveReturnType(compiler, content);
		Node block = buildConcreteBlock(compiler, content, parameters);
		functions.add(declarations.current().toStruct(parameters, returnType, block), generator);
		return new EmptyNode();
	}

	private Set<Parameter> parseAllParameters(Compiler compiler, String content) {
		Set<Parameter> parameters = new HashSet<>();
		parameters.addAll(parseParameters(compiler, content));
		parameters.addAll(buildParentParameters());
		parameters.forEach(declarations::define);
		return parameters;
	}

	private Type resolveReturnType(Compiler compiler, String content) {
		int returnTypeStart = content.indexOf("=>") + 2;
		int returnTypeEnd = hasImplementation(content) ? content.indexOf(':') : content.length();
		String returnTypeString = content.substring(returnTypeStart, returnTypeEnd);
		return compiler.resolveName(returnTypeString);
	}

	private Node buildConcreteBlock(Compiler compiler, String content, Set<Parameter> parameters) {
		if (hasImplementation(content)) {
			return buildBlock(compiler, content, parameters);
		} else {
			throw new UnsupportedOperationException("Abstract methods are not supported yet.");
		}
	}

	private Collection<? extends Parameter> parseParameters(Compiler compiler, String content) {
		int paramStart = content.indexOf('(') + 1;
		int paramEnd = content.indexOf(')');
		String paramsString = content.substring(paramStart, paramEnd);
		return Arrays.stream(paramsString.split(","))
				.map(String::trim)
				.filter(paramString -> !paramString.isBlank())
				.map(value -> Parameter.of(value, compiler))
				.collect(Collectors.toSet());
	}

	private Set<Parameter> buildParentParameters() {
		return declarations.stream()
				.filter(declaration -> !declarations.isRoot(declaration) &&
						!declarations.isCurrent(declaration))
				.map(Declaration::toInstancePair)
				.collect(Collectors.toSet());
	}

	private boolean hasImplementation(String content) {
		return content.contains(":");
	}

	private Node buildBlock(Compiler compiler, String content, Set<Parameter> parameters) {
		Node block = parseBlock(compiler, content);
		Declaration current = declarations.current();
		buildInstance(parameters, block, current.declareInstance(parameters.size()), current);
		return block;
	}

	private Node parseBlock(Compiler compiler, String content) {
		String blockString = content.substring(content.indexOf(':') + 1);
		Node impl = compiler.parseSingle(blockString);
		return impl.isParent() ? impl : new BlockNode(Collections.singleton(impl));
	}

	private void buildInstance(Set<? extends Parameter> parameters, Node block,
	                           Node instanceDeclaration, Declaration current) {
		Collection<Node> nodes = current.buildAssignments(new ArrayList<>(parameters));
		nodes.add(instanceDeclaration);
		Deque<Node> children = block.children();
		nodes.forEach(children::addFirst);
	}
}

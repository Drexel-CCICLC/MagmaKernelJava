package com.meti.node.struct;

import com.meti.Cache;
import com.meti.Compiler;
import com.meti.Unit;
import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.Parameter;
import com.meti.node.Type;
import com.meti.node.declare.Declaration;
import com.meti.node.declare.Declarations;
import com.meti.node.declare.DeclareNode;
import com.meti.node.primitive.VoidType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StructUnit implements Unit {
	private static final List<String> MARKERS = List.of("(", "=>", ":");
	private final Cache cache;
	private final Declarations declarations;

	public StructUnit(Declarations declarations, Cache cache) {
		this.declarations = declarations;
		this.cache = cache;
	}

	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		return Optional.of(content)
				.map(String::trim)
				.map(s -> new StringIndexBuffer(content, MARKERS))
				.filter(IndexBuffer::isValid)
				.map(buffer -> buildFunction(compiler, buffer))
				.map(cache::addFunction);
	}

	private Node buildFunction(Compiler compiler, IndexBuffer buffer) {
		Collection<Parameter> parameters = parseParameters(compiler, buffer);
		Type returnType = parseReturnType(compiler, buffer);
		Node block = parseBlock(compiler, buffer);
		String funcName = String.join("_", declarations.getStack());
		return new FunctionNode(funcName, returnType, parameters, block);
	}

	private Collection<Parameter> parseParameters(Compiler compiler, IndexBuffer buffer) {
		return buffer.cutIfPresent(0)
				.stream()
				.filter(s -> s.startsWith("(") && s.endsWith(")"))
				.map(s -> s.substring(1, s.length() - 1))
				.map(s -> s.split(","))
				.flatMap(Arrays::stream)
				.map(paramString -> parseParam(compiler, paramString))
				.peek(declarations::define)
				.collect(Collectors.toList());
	}

	private Type parseReturnType(Compiler compiler, IndexBuffer buffer) {
		return buffer.cutIfPresent(1)
				.filter(s -> s.startsWith("=>"))
				.map(s -> s.substring(2))
				.map(compiler::resolveName)
				.orElse(VoidType.INSTANCE);
	}

	private Node parseBlock(Compiler compiler, IndexBuffer buffer) {
		if (buffer.isPresent(2)) {
			return Optional.of(buffer)
					.map(b -> b.cut(2))
					.map(String::trim)
					.map(s -> s.substring(1))
					.map(String::trim)
					.filter(s -> s.startsWith("{") && s.endsWith("}"))
					.map(s -> parseValidBlock(compiler, s))
					.orElseThrow(() -> new ParseException("Single statement methods are not supported yet."));
		}
		throw new ParseException("Abstract methods are not supported yet.");
	}

	private Parameter parseParam(Compiler compiler, String paramString) {
		int lastSpace = paramString.lastIndexOf(' ');
		String type = paramString.substring(0, lastSpace);
		String name = paramString.substring(lastSpace + 1);
		return Parameter.create(compiler.resolveName(type), name);
	}

	private Node parseValidBlock(Compiler compiler, String implString) {
		Deque<Node> statements = parseStatements(compiler, implString);
		Declaration current = declarations.current();
		if (current.isParent()) statements.addFirst(assign(current));
		return new BlockNode(statements);
	}

	private Deque<Node> parseStatements(Compiler compiler, String implString) {
		return Stream.of(implString)
				.map(s -> implString.substring(1, implString.length() - 1))
				.flatMap(this::partition)
				.filter(s -> !s.isBlank())
				.map(compiler::parse)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	private Node assign(Declaration current) {
		cache.addStruct(current.toStruct());
		cache.addStruct(new DeclareNode(new StructType(current.getName()), current.instanceName(), null));
		return current.declareInstance();
	}

	private Stream<String> partition(String childString) {
		Collection<String> partitions = new ArrayList<>();
		StringBuilder current = new StringBuilder();
		int depth = 0;
		for (char c : childString.toCharArray()) {
			if (';' == c && 0 == depth) {
				partitions.add(current.toString());
				current = new StringBuilder();
			} else {
				if ('{' == c) {
					depth++;
				}
				if ('}' == c) {
					depth--;
				}
				current.append(c);
			}
		}
		partitions.add(current.toString());
		return partitions.stream();
	}

	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		return Optional.of(content)
				.map(String::trim)
				.map(s -> new StringIndexBuffer(content, MARKERS))
				.filter(IndexBuffer::isValid)
				.map(indexBuffer -> extractType(compiler, indexBuffer));
	}

	private Type extractType(Compiler compiler, IndexBuffer indexBuffer) {
		Collection<Parameter> parameters = parseParameters(compiler, indexBuffer);
		Type returnType = parseReturnType(compiler, indexBuffer);
		return new FunctionType(parameters, returnType, declarations.currentName());
	}
}

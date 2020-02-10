package com.meti.node.struct;

import com.meti.Cache;
import com.meti.Compiler;
import com.meti.Unit;
import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.Parameter;
import com.meti.node.Type;
import com.meti.node.declare.AssignNode;
import com.meti.node.declare.VariableNode;
import com.meti.node.primitive.special.VoidType;
import com.meti.node.struct.invoke.InvocationNode;
import com.meti.node.struct.type.FunctionTypeImpl;
import com.meti.parse.Declaration;
import com.meti.parse.Declarations;
import com.meti.util.BracketPartitioner;
import com.meti.util.EmptyNode;
import com.meti.util.IndexBuffer;
import com.meti.util.StringIndexBuffer;

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
		if (declarations.current().isNative()) {
			return new EmptyNode();
		} else {
			Collection<Parameter> parameters = parseParameters(compiler, buffer);
			Type returnType = parseReturnType(compiler, buffer);
			Node block = parseBlock(compiler, buffer);
			String funcName = declarations.buildStackName();
			return new FunctionNode(funcName, returnType, parameters, block);
		}
	}

	private Collection<Parameter> parseParameters(Compiler compiler, IndexBuffer buffer) {
		Collection<Parameter> parameters = new ArrayList<>();
		parseGivenParameters(compiler, buffer)
				.peek(declarations::define)
				.forEach(parameters::add);
		parameters.addAll(declarations.buildStackParameters());
		return parameters;
	}

	private Type parseReturnType(Compiler compiler, IndexBuffer buffer) {
		Optional<String> s1 = buffer.cutIfPresent(1);
		return s1.filter(s -> s.startsWith("=>"))
				.map(s -> s.substring(2))
				.map(compiler::resolveName)
				.orElseGet(this::buildMissingReturnType);
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

	private Stream<Parameter> parseGivenParameters(Compiler compiler, IndexBuffer buffer) {
		return buffer.cutIfPresent(0)
				.stream()
				.map(String::trim)
				.filter(s -> s.startsWith("(") && s.endsWith(")"))
				.map(s -> s.substring(1, s.length() - 1))
				.map(s -> s.split(","))
				.flatMap(Arrays::stream)
				.filter(s -> !s.isBlank())
				.map(paramString -> parseParam(compiler, paramString));
	}

	private Type buildMissingReturnType() {
		return declarations.isInClass() ? declarations.toLazyStruct() : VoidType.INSTANCE;
	}

	private Node parseValidBlock(Compiler compiler, String implString) {
		Deque<Node> statements = parseStatements(compiler, implString);
		Declaration current = declarations.current();
		if (current.isSuperStructure()) statements.addFirst(assign(current));
		if (declarations.isInClass()) {
			statements.addLast(new ReturnNode(new VariableNode(declarations.currentName() + "_")));
		}
		if (declarations.isInSingleton()) {
			String name = current.name();
			String varName = name.substring(0, name.length() - 1);
			cache.addFunction(compiler.parse("val " + varName + "={}"));
			cache.add(new AssignNode(new VariableNode(varName), new InvocationNode(new VariableNode(name),
					Collections.emptyList())));
		}
		return new BlockNode(statements);
	}

	private Parameter parseParam(Compiler compiler, String paramString) {
		int lastSpace = paramString.lastIndexOf(' ');
		String type = paramString.substring(0, lastSpace);
		String name = paramString.substring(lastSpace + 1);
		return Parameter.create(compiler.resolveName(type), Collections.singletonList(name));
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
		return current.declareInstance();
	}

	private Stream<String> partition(String childString) {
		return new BracketPartitioner(childString)
				.partition()
				.stream();
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
				.filter(StringIndexBuffer::isValid)
				.map(indexBuffer -> extractType(compiler, indexBuffer));
	}

	private Type extractType(Compiler compiler, IndexBuffer indexBuffer) {
		Collection<Parameter> parameters = parseGivenParameters(compiler, indexBuffer)
				.collect(Collectors.toList());
		Type returnType = parseReturnType(compiler, indexBuffer);
		return new FunctionTypeImpl(parameters, returnType, declarations.currentName());
	}
}

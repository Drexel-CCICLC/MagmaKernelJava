package com.meti;

import com.meti.exception.ParseException;
import com.meti.primitive.VoidType;

import java.util.*;
import java.util.stream.IntStream;

public class StructParser implements Parser {
	private final Cache cache;
	private final Declarations declarations;
	private int implStart = 0;
	private int paramStart = 0;
	private int returnStart = 0;

	public StructParser(Declarations declarations, Cache cache) {
		this.declarations = declarations;
		this.cache = cache;
	}

	@Override
	public Optional<Node> parse(String content, Compiler compiler) throws ParseException {
		String trim = content.trim();
		paramStart = trim.indexOf('(');
		returnStart = trim.indexOf("=>");
		implStart = trim.indexOf(':');
		if (allEmpty(paramStart, returnStart, implStart) || !isZero(paramStart, returnStart, implStart)) {
			return Optional.empty();
		} else {
			cache.addFunction(buildFunction(compiler, trim));
			return Optional.of(new EmptyNode());
		}
	}

	private boolean allEmpty(int paramIndex, int returnIndex, int implIndex) {
		return IntStream.of(paramIndex, returnIndex, implIndex)
				.allMatch(value -> -1 == value);
	}

	private boolean isZero(int paramStart, int returnStart, int implStart) {
		return IntStream.of(paramStart, returnStart, implStart)
				.anyMatch(value -> value == 0);

	}

	private Node buildFunction(Compiler compiler, String trim) throws ParseException {
		Collection<Parameter> parameters = parseParameters(trim, compiler);
		Type returnType = parseReturnType(trim, compiler);
		Node block = parseBlock(trim, compiler);
		String funcName = String.join("_", declarations.getStack());
		return new FunctionNode(funcName, returnType, parameters, block);
	}

	private Collection<Parameter> parseParameters(String content, Compiler compiler) {
		Collection<Parameter> parameters = new ArrayList<>();
		if (-1 != paramStart) {
			int paramEnd = endOf(content, returnStart, implStart);
			String paramsString = content.substring(paramStart, paramEnd).trim();
			if (paramsString.startsWith("(") && paramsString.endsWith(")")) {
				Arrays.stream(paramsString.substring(1, paramsString.length() - 1).split(","))
						.map(s -> parseParam(s, compiler))
						.peek(declarations::define)
						.forEach(parameters::add);
			}
		}
		return parameters;
	}

	private Type parseReturnType(String content, Compiler compiler) {
		Type returnType = VoidType.INSTANCE;
		if (-1 != returnStart) {
			int returnEnd = endOf(content, implStart);
			String returnString = content.substring(returnStart, returnEnd).trim();
			if (returnString.startsWith("=>")) {
				returnType = compiler.resolveName(returnString.substring(2));
			}
		}
		return returnType;
	}

	private Node parseBlock(String content, Compiler compiler) throws ParseException {
		if (-1 == implStart) throw new ParseException("Abstract methods are not supported yet.");
		String implString = content.substring(implStart).trim().substring(1).trim();
		if (implString.startsWith("{") && implString.endsWith("}")) {
			return parseValidBlock(compiler, implString);
		} else {
			throw new ParseException("Single statement methods are not supported yet.");
		}
	}

	private int endOf(CharSequence content, int... indices) {
		return IntStream.concat(IntStream.of(indices), IntStream.of(content.length()))
				.filter(value -> -1 != value)
				.min().orElseThrow();
	}

	private Parameter parseParam(String s, Compiler compiler) {
		int lastSpace = s.lastIndexOf(' ');
		String type = s.substring(0, lastSpace);
		String name = s.substring(lastSpace + 1);
		return Parameter.create(compiler.resolveName(type), name);
	}

	private Node parseValidBlock(Compiler compiler, String implString) throws ParseException {
		Deque<Node> statements = new LinkedList<>(parseStatements(compiler, implString));
		Declaration current = declarations.current();
		if (current.isParent()) statements.addFirst(assign(current));
		return new BlockNode(statements);
	}

	private Collection<Node> parseStatements(Compiler compiler, String implString) throws ParseException {
		Collection<Node> statements = new ArrayList<>();
		String childString = implString.substring(1, implString.length() - 1);
		Collection<String> partitions = partition(childString);
		for (String s : partitions) {
			if (!s.isBlank()) {
				Node node = compiler.parse(s);
				statements.add(node);
			}
		}
		return statements;
	}

	private AssignNode assign(Declaration current) {
		cache.addStruct(current.toStruct());
		cache.addStruct(new DeclareNode(new StructType(current.getName()), current.instanceName(), null));
		return current.assignField();
	}

	private Collection<String> partition(String childString) {
		Collection<String> partitions = new ArrayList<>();
		StringBuilder current = new StringBuilder();
		int depth = 0;
		for (char c : childString.toCharArray()) {
			if (c == ';' && depth == 0) {
				partitions.add(current.toString());
				current = new StringBuilder();
			} else {
				if (c == '{') {
					depth++;
				}
				if (c == '}') {
					depth--;
				}
				current.append(c);
			}
		}
		partitions.add(current.toString());
		return partitions;
	}
}

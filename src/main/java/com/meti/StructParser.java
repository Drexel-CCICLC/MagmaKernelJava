package com.meti;

import com.meti.exception.ParseException;
import com.meti.primitive.VoidType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
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
			Collection<Parameter> parameters = parseParameters(trim, compiler);
			Type returnType = parseReturnType(trim, compiler);
			Node block = parseBlock(trim, compiler);
			Node function = new FunctionNode(declarations.current().getName(), returnType, parameters, block);
			cache.addFunction(function);
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
		Collection<Node> statements = new ArrayList<>();
		if (-1 != implStart) {
			String implString = content.substring(implStart).trim().substring(1).trim();
			if (implString.startsWith("{") && implString.endsWith("}")) {
				String[] split = implString.substring(1, implString.length() - 1).split(";");
				for (String s : split) {
					if (!s.isBlank()) {
						Node node = compiler.parse(s);
						statements.add(node);
					}
				}
			} else {
				throw new ParseException("Single statement methods are not supported yet.");
			}
		} else {
			throw new ParseException("Abstract methods are not supported yet.");
		}
		return new BlockNode(statements);
	}

	private int endOf(String content, int... indices) {
		return IntStream.concat(IntStream.of(indices), IntStream.of(content.length()))
				.filter(value -> -1 != value)
				.min().orElseThrow();
	}

	private Parameter parseParam(String s, Compiler compiler) {
		int lastSpace = s.lastIndexOf(' ');
		String type = s.substring(0, lastSpace);
		String name = s.substring(lastSpace + 1);
		return new Parameter(compiler.resolveName(type), name);
	}
	/*
		Valid:
		val empty =:{
		}
		val accept = (Any object) :{
			//impl
		}
		val add = (Int value0, Int Value1) : value0 + value1;
		void someAbstractMethod ==> Void;
		void doAThing () => Void :{}

		Invalid:
		val empty =;
	 */


}

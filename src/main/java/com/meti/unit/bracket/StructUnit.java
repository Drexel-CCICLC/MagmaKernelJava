package com.meti.unit.bracket;

import com.meti.Aliaser;
import com.meti.Compiler;
import com.meti.unit.Data;
import com.meti.unit.Declarations;
import com.meti.unit.Unit;

import java.util.*;
import java.util.stream.Collectors;

public class StructUnit implements Unit {
	private final Aliaser aliaser;
	private final Declarations declarations;
	private final Stack<String> stack;

	public StructUnit(Data data, DeclareStack declareStack) {
		this.aliaser = data.getAliaser();
		this.declarations = data.getDeclarations();
		this.stack = declareStack.getStack();
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		if (trimmedInput.startsWith("(")) {
			return parseStruct(compiler, trimmedInput);
		}
		return Optional.empty();
	}

	private Optional<String> parseStruct(Compiler compiler, String trimmedInput) {
		int opening = trimmedInput.indexOf('(');
		int closing = trimmedInput.indexOf(')', 1);
		List<String> params = parseParams(trimmedInput, opening, closing);
		return trimmedInput.length() != closing + 1 && trimmedInput.indexOf(':') > closing ?
				parseStruct(compiler, trimmedInput, params) :
				Optional.of("");
	}

	private List<String> parseParams(String trimmedInput, int opening, int closing) {
		String paramString = trimmedInput.substring(opening + 1, closing);
		return Arrays.stream(paramString.split(","))
				.map(String::trim)
				.filter(string -> !string.isEmpty())
				.collect(Collectors.toList());
	}

	private Optional<String> parseStruct(Compiler compiler, String trimmedInput, Collection<String> params) {
		//TODO: add typing for parameters
		params.forEach(s -> declarations.define(null, stack, s));
		String compiledContent = compileContent(compiler, trimmedInput);
		params.forEach(s -> declarations.delete(stack, s));
		String joinedParams = joinParams(params);
		return Optional.of("function(" + joinedParams + ")" + compiledContent);
	}

	private String compileContent(Compiler compiler, String trimmedInput) {
		String content = trimmedInput.substring(trimmedInput.indexOf(':') + 1);
		String compiledContent = compiler.compile(content);
		if (!compiledContent.startsWith("{")) compiledContent = "{" + compiledContent + "}";
		return compiledContent;
	}

	private String joinParams(Collection<String> params) {
		return params.stream()
				.map(aliaser::alias)
				.collect(Collectors.joining(","));
	}
}

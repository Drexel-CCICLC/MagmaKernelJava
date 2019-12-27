package com.meti.unit.bracket;

import com.meti.Aliaser;
import com.meti.Compiler;
import com.meti.Declarations;
import com.meti.exception.ParseException;
import com.meti.type.Type;
import com.meti.unit.Data;
import com.meti.unit.Unit;

import java.util.*;
import java.util.stream.Collectors;

public class StructUnit implements Unit {
	private final Aliaser aliaser;
	private final Declarations manager;

	public StructUnit(Data data) {
		this.aliaser = data.getAliaser();
		this.manager = data.getManager();
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		return trimmedInput.startsWith("(") ?
				parseStruct(compiler, trimmedInput) :
				Optional.empty();
	}

	private Optional<String> parseStruct(Compiler compiler, String trimmedInput) {
		int opening = trimmedInput.indexOf('(');
		int closing = trimmedInput.indexOf(')', 1);
		List<List<String>> params = parseParams(trimmedInput, opening, closing);
		return trimmedInput.length() != closing + 1 && trimmedInput.indexOf(':') > closing ?
				parseStruct(compiler, trimmedInput, params) :
				Optional.of("");
	}

	private List<List<String>> parseParams(String trimmedInput, int opening, int closing) {
		String paramString = trimmedInput.substring(opening + 1, closing);
		return Arrays.stream(paramString.split(","))
				.map(String::trim)
				.filter(string -> !string.isEmpty())
				.map(string -> string.split(" "))
				.map(List::of)
				.collect(Collectors.toList());
	}

	private Optional<String> parseStruct(Compiler compiler, String trimmedInput, List<List<String>> params) {
		//TODO: add typing for parameters
		Collection<String> paramList = new ArrayList<>();
		for (List<String> param : params) {
			String nameString = define(compiler, param);
			paramList.add(nameString);
		}
		String compiledContent = compileContent(compiler, trimmedInput);
		paramList.forEach(manager::delete);
		String joinedParams = joinParams(paramList);
		return Optional.of("function(" + joinedParams + ")" + compiledContent);
	}

	private String define(Compiler compiler, List<String> args) {
		if (args.size() < 2) throw new ParseException("Could not find name or type.");
		List<String> flags = args.subList(0, args.size() - 2);
		String typeString = args.get(args.size() - 2);
		String nameString = args.get(args.size() - 1);
		Type type = compiler.resolve(typeString);
		manager.define(nameString, type, flags);
		return nameString;
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

	@Override
	public Optional<Type> resolve(String input, Compiler compiler) {
		return Optional.empty();
	}
}

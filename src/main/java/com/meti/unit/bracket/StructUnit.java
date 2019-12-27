package com.meti.unit.bracket;

import com.meti.Aliaser;
import com.meti.Compiler;
import com.meti.Declarations;
import com.meti.exception.DoesNotExistException;
import com.meti.exception.ParseException;
import com.meti.type.Type;
import com.meti.type.TypeStack;
import com.meti.unit.Data;
import com.meti.unit.Unit;
import com.meti.unit.value.RecursiveType;

import java.util.*;
import java.util.stream.Collectors;

public class StructUnit implements Unit {
	private final Aliaser aliaser;
	private final Declarations manager;
	private final TypeStack typeStack;

	public StructUnit(Data data) {
		this.aliaser = data.getAliaser();
		this.manager = data.getManager();
		this.typeStack = data.getTypeStack();
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
		return  parseStruct(compiler, trimmedInput, params);
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
		Map<String, Type> paramMap = new HashMap<>();
		for (List<String> param : params) {
			Map.Entry<String, Type> nameString = createParam(compiler, param);
			paramMap.put(nameString.getKey(), nameString.getValue());
		}
		int returnStart = trimmedInput.indexOf(')', 1) + 1;
		int returnEnd = trimmedInput.indexOf(':') == -1 ? trimmedInput.length() : trimmedInput.indexOf(':');
		String returnString = trimmedInput.substring(returnStart, returnEnd).trim();
		if (!returnString.startsWith("=>")) {
			throw new DoesNotExistException("No return type:" + returnString);
		}
		Type returnType = compiler.resolve(returnString.substring(2).trim());
		typeStack.add(new StructType(returnType, paramMap.values()));
		int contentEnd = trimmedInput.indexOf(')', 1);
		if (trimmedInput.length() != contentEnd + 1 && trimmedInput.indexOf(':') > contentEnd) {
			String compiledContent = compileContent(compiler, trimmedInput);
			if (!(returnType instanceof RecursiveType)) {
				paramMap.keySet().forEach(manager::delete);
			}
			String joinedParams = joinParams(paramMap.keySet());
			return Optional.of("function(" + joinedParams + ")" + compiledContent);
		} else {
			return Optional.of("");
		}
	}

	private Map.Entry<String, Type> createParam(Compiler compiler, List<String> args) {
		if (args.size() < 2) throw new ParseException("Could not find type in: " + String.join(" ", args));
		List<String> flags = args.subList(0, args.size() - 2);
		String typeString = args.get(args.size() - 2);
		String nameString = args.get(args.size() - 1);
		Type type = compiler.resolve(typeString);
		manager.define(nameString, type, flags);
		return Map.entry(nameString, type);
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

	private static class StructType implements Type {
		private final Collection<Type> paramType;
		private final Type returnType;

		private StructType(Type returnType, Collection<Type> paramType) {
			this.returnType = returnType;
			this.paramType = paramType;
		}

		@Override
		public Collection<Type> children() {
			return paramType;
		}

		@Override
		public Optional<Type> parent() {
			return Optional.empty();
		}

		@Override
		public Optional<Type> returnType() {
			return Optional.of(returnType);
		}
	}
}

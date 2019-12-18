package com.meti.unit.bracket;

import com.meti.Aliaser;
import com.meti.Compiler;
import com.meti.unit.Data;
import com.meti.unit.Declarations;
import com.meti.unit.Unit;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StructUnit implements Unit {
	private final Aliaser aliaser;
	private Declarations declarations;

	public StructUnit(Data data) {
		this.aliaser = data.getAliaser();
		this.declarations = data.getDeclarations();
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		if (trimmedInput.startsWith("(")) {
			int opening = trimmedInput.indexOf('(');
			int closing = trimmedInput.indexOf(')', 1);
			String paramString = trimmedInput.substring(opening + 1, closing);
			List<String> params = Arrays.stream(paramString.split(","))
					.map(String::trim)
					.filter(string -> !string.isEmpty())
					.collect(Collectors.toList());
			String joinedParams = params.stream()
					.map(aliaser::alias)
					.collect(Collectors.joining(","));
			if (trimmedInput.length() != closing + 1 && trimmedInput.indexOf(':') > closing) {
				String content = trimmedInput.substring(trimmedInput.indexOf(':') + 1);
				params.forEach(declarations::define);
				String compiledContent = compiler.compile(content);
				if (!compiledContent.startsWith("{")) {
					compiledContent = "{" + compiledContent + "}";
				}
				params.forEach(declarations::delete);
				return Optional.of("function(" + joinedParams + ")" + compiledContent);
			}
			else {
				return Optional.of("");
			}
		}
		return Optional.empty();
	}
}

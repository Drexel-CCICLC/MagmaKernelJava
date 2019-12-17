package com.meti.unit;

import com.meti.Aliaser;
import com.meti.Compiler;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class StructUnit implements Unit {
	private final Aliaser aliaser;

	public StructUnit(Aliaser aliaser) {
		this.aliaser = aliaser;
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		if(trimmedInput.startsWith("(")) {
			int opening = trimmedInput.indexOf('(');
			int closing = trimmedInput.indexOf(')', 1);
			String paramString = trimmedInput.substring(opening + 1, closing);
			String joinedParams = Arrays.stream(paramString.split(","))
					.filter(string -> !string.isBlank())
					.map(aliaser::alias)
					.collect(Collectors.joining(","));
			if(trimmedInput.charAt(closing + 1) == ':') {
				String content = trimmedInput.substring(closing + 2);
				String compiledContent = compiler.compile(content);
				if(!compiledContent.startsWith("{")) {
					compiledContent = "{" + compiledContent + "}";
				}
				return Optional.of("function(" + joinedParams + ")" + compiledContent);
			}
		}
		return Optional.empty();
	}
}

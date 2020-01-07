package com.meti;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeclareUnit implements Unit {
	private final StringBuilder callback;
	private final Declarations declarations;

	public DeclareUnit(StringBuilder callback, Declarations declarations) {
		this.callback = callback;
		this.declarations = declarations;
	}

	@Override
	public boolean canCompile(String value) {
		int equals = value.indexOf('=');
		if (equals != -1) {
			List<String> flags = getFlags(value);
			return flags.contains("val") || flags.contains("var");
		}
		return false;
	}

	private List<String> getFlags(String value) {
		return List.of(value.substring(0, value.indexOf('=')).split(" "));
	}

	@Override
	public String compile(String trim, Compiler compiler) {
		int index = trim.indexOf('=');
		String first = trim.substring(0, index).trim();
		int lastSpace = first.lastIndexOf(' ');
		String flagString = first.substring(0, lastSpace);
		List<Flag> flags = Arrays.stream(flagString.split(" "))
				.map(String::toUpperCase)
				.map(Flag::valueOf)
				.collect(Collectors.toList());
		String name = first.substring(lastSpace + 1);
		String value = trim.substring(index + 1);
		declarations.push(name);
		Type type = compiler.resolveValue(value);
		String renderType = type.render();
		declarations.defineSibling(name, type, flags);
		String compiledValue = compiler.compileOnly(value);
		declarations.pop();
		if (name.equals("main") || (compiledValue.isBlank() && flags.contains(Flag.NATIVE))) {
			return "";
		} else if (renderType.endsWith(")")) {
			return "";
		} else {
			return renderType + " " + name + "=" + compiledValue + ";";
		}
	}

	@Override
	public Optional<Type> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}
}

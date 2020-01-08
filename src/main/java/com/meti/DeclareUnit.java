package com.meti;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeclareUnit implements Unit {
	private final Declarations declarations;

	public DeclareUnit(Declarations declarations) {
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
		StringBuilder builder = new StringBuilder();
		declarations.defineSibling(name, type, flags, builder);
		String compiledValue = compiler.compileOnly(value);
		declarations.pop();
		if (isRenderable(flags, name, renderType, compiledValue)) {
			return builder.toString();
		} else {
			return builder + renderType + " " + name + "=" + compiledValue + ";";
		}
	}

	private boolean isRenderable(List<Flag> flags, String name, String renderType, String compiledValue) {
        return name.equals("main") || isNative(flags, compiledValue) || renderType.endsWith(")");
	}

	private boolean isNative(List<Flag> flags, String compiledValue) {
		return compiledValue.isBlank() && flags.contains(Flag.NATIVE);
	}

	@Override
	public Optional<? extends Type> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}
}

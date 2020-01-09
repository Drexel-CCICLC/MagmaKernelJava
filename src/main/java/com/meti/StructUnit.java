package com.meti;

import java.util.*;
import java.util.stream.Collectors;

import static com.meti.TreeDeclarationBuilder.create;

public class StructUnit implements CompoundUnit {
	private static final String EMPTY_STRING = "";
	private final Declarations declarations;

	public StructUnit(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<? extends Type> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.of(value)
				.filter(this::canCompile)
				.flatMap(s -> resolveStructType(s, compiler));
	}

	@Override
	public boolean canCompile(String value) {
		int equals = value.indexOf('=');
		int returnIndex = value.indexOf("=>");
		boolean hasReturnValue = returnIndex != -1 && returnIndex <= equals;
		return value.contains(":") || hasReturnValue;
	}

	private Optional<Type> resolveStructType(String value, Compiler compiler) {
		Type returnType = parseReturnType(value, compiler);
		String currentName = declarations.current().render();
		List<Type> params = paramTypeString(value, compiler);
		String paramString = joinParamTypes(params);
		return Optional.of(returnType.render() + "(*" + currentName + ")(" + paramString + ")")
				.map(s -> new StructType());
	}

	private Type parseReturnType(String value, Compiler compiler) {
		int index = index(value);
		if (index == -1) index = value.length();
		String header = value.substring(0, index);
		int returnIndex = header.indexOf("=>");
		String returnTypeName = header.substring(returnIndex + 2);
		return compiler.resolveName(returnTypeName);
	}

	private List<Type> paramTypeString(String value, Compiler compiler) {
		int start = value.indexOf('(');
		int end = value.indexOf(')');
		if (start == -1 || end == -1) return Collections.emptyList();
		String params = value.substring(start + 1, end);
		return collectTypes(compiler, params);
	}

	private String joinParamTypes(Collection<Type> params) {
		return params.stream()
				.map(Type::render)
				.map(s -> s + "*")
				.collect(Collectors.joining(","));
	}

	private int index(String value) {
		return value.indexOf(':');
	}

	private List<Type> collectTypes(Compiler compiler, String paramString) {
		String[] params = paramString.split(",");
		return Arrays.stream(params)
				.filter(param -> !param.isBlank())
				.map(param -> resolveTypeName(compiler, param))
				.collect(Collectors.toList());
	}

	private Type resolveTypeName(Compiler compiler, String paramString) {
		int index = paramString.lastIndexOf(' ');
		String name = paramString.substring(0, index);
		return compiler.resolveName(name);
	}

	@Override
	public String compile(String value, Compiler compiler) {
		Declaration current = declarations.current();
		String name = current.render();
		int index = index(value);
		if (index != -1) append(value, compiler, name);
		return EMPTY_STRING;
	}

	private void append(String value, Compiler compiler, String name) {
		int index = index(value);
		String header = buildHeader(name, value, compiler);
		String content = value.substring(index + 1);
		appendContent(name, header, content, compiler);
	}

	private String buildHeader(String name, String value, Compiler compiler) {
		String returnType = parseReturnType(value, compiler).render();
		String paramString = "(" + parseParams(value, compiler) + ")";
		return returnType + " " + name + paramString;
	}

	private void appendContent(String name, String header, String content, Compiler compiler) {
		String result = header + compileContent(compiler, name, content);
		Declaration parent = declarations.parent();
		parent.callback().append(result);
	}

	private String parseParams(String value, Compiler compiler) {
		int start = value.indexOf('(');
		int end = value.indexOf(')');
		if (start == -1 || end == -1) return "";
		String paramsString = value.substring(start + 1, end);
		return parseParamString(paramsString, compiler);
	}

	private String compileContent(Compiler compiler, String name, String content) {
		return name.equals("main") ?
				declarations.renderInParentScope(() -> compiler.compileOnly(content)) :
				compiler.compileOnly(content);
	}

	private String parseParamString(String paramsString, Compiler compiler) {
		String[] paramStrings = paramsString.split(",");
		return Arrays.stream(paramStrings)
				.filter(param -> !param.isBlank())
				.map(param -> buildParamString(param, compiler))
				.collect(Collectors.joining());
	}

	private String buildParamString(String param, Compiler compiler) {
		int last = param.lastIndexOf(' ');
		String typeString = param.substring(0, last);
		String name = param.substring(last + 1);
		Type type = compiler.resolveName(typeString);
		declarations.define(createBuilder(name, type));
		return type.render() + "* " + name;
	}

	private DeclarationBuilder createBuilder(String name, Type type) {
		return create().withType(type)
				.withName(name);
	}

	private static class StructType implements Type {
		@Override
		public String render() {
			throw new UnsupportedOperationException();
		}
	}
}

package com.meti;

import java.util.Optional;

public class ArrayUnit implements CompoundUnit {
	@Override
	public Optional<? extends Type> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.of(value)
				.filter(this::canCompile)
				.map(String::trim)
				.map(trim -> parseType(trim, compiler))
				.map(ArrayType::new);
	}

	@Override
	public boolean canCompile(String value) {
		return value.trim().startsWith("Array");
	}

	@Override
	public String compile(String value, Compiler compiler) {
		String trim = value.trim();
		String type = parseTypeString(compiler, trim);
		String size = parseSize(compiler, trim);
		return "malloc(" + size + "*sizeof(" + type + "))";
	}

	private String parseTypeString(Compiler compiler, String trim) {
		Type type = parseType(trim, compiler);
		return type.render();
	}

	private String parseSize(Compiler compiler, String trim) {
		int sizeStart = trim.indexOf('(');
		int sizeEnd = trim.indexOf(')');
		String size = trim.substring(sizeStart + 1, sizeEnd);
		return compiler.compileOnly(size);
	}

	private Type parseType(String trim, Compiler compiler) {
		int start = trim.indexOf('<');
		int end = trim.indexOf('>');
		String name = trim.substring(start + 1, end);
		return compiler.resolveName(name);
	}

	private static final class ArrayType implements ParentType {
		private final Type child;

		private ArrayType(Type child) {
			this.child = child;
		}

		@Override
		public Optional<Type> child() {
			return Optional.ofNullable(child);
		}

		@Override
		public String render() {
			return child.render() + "*";
		}
	}
}

package com.meti.unit;

import com.meti.compile.ComplexCompiler;

import java.util.Collection;

public class ParentUnit implements Unit {
	protected final Collection<Unit> children;

	public ParentUnit(Collection<Unit> children) {
		this.children = children;
	}

	@Override
	public boolean canCompile(String value) {
		return children.stream()
				.anyMatch(child -> child.canCompile(value));
	}

	@Override
	public String compile(String value, ComplexCompiler compiler) {
		return children.stream()
				.filter(child -> child.canCompile(value))
				.map(child -> child.compile(value, compiler))
				.findAny()
				.orElseThrow();
	}
}

package com.meti.unit;

import com.meti.compile.ComplexCompiler;

public interface Unit {
	boolean canCompile(String value);

	String compile(String value, ComplexCompiler compiler);
}

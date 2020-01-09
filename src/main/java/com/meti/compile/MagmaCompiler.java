package com.meti.compile;

import com.meti.declare.Declarations;
import com.meti.declare.TreeDeclarations;
import com.meti.unit.MagmaUnit;

import java.util.Collections;

public class MagmaCompiler implements Compiler {
	protected final Compiler compiler = new UnitCompiler(Collections.singletonList(new MagmaUnit()));
	private final Declarations declarations = new TreeDeclarations();

	@Override
	public String compileAll(String value) {
		String result = compiler.compileAll(value);
		return declarations.root().callback() + result;
	}

	@Override
	public String compileOnly(String value) {
		String result = compiler.compileOnly(value);
		return declarations.root().callback() + result;
	}
}

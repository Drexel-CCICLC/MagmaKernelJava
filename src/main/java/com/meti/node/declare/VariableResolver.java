package com.meti.node.declare;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.exception.ParseException;
import com.meti.node.Type;
import com.meti.node.struct.type.StructType;
import com.meti.parse.Declaration;
import com.meti.parse.Declarations;

import java.util.Optional;
import java.util.function.Function;

public class VariableResolver implements Resolver {
	private final Declarations declarations;

	public VariableResolver(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		String trim = content.trim();
		return Optional.ofNullable(trim.contains(".") ?
				parseAccesor(compiler, trim) :
				parseNormal(trim));
	}

	private Type parseAccesor(Compiler compiler, String trim) {
		Type parentType = parentType(compiler, trim);
		if (parentType instanceof StructType) {
			return parseChild((StructType) parentType, trim);
		} else {
			throw new ParseException(parentType + " is not an object.");
		}
	}

	private Type parseNormal(String trim) {
		return declarations.relative(trim + "$")
				.flatMap((Function<Declaration, Optional<Declaration>>) declaration -> declaration.child(trim))
				.map(Declaration::type)
				.orElse(resolveLocal(trim));
	}

	private Type parentType(Compiler compiler, String trim) {
		String parent = trim.substring(0, trim.indexOf('.'));
		return compiler.resolveValue(parent);
	}

	private Type parseChild(StructType parentType, String trim) {
		String child = trim.substring(trim.indexOf('.') + 1);
		return parentType.typeOf(child).orElseThrow();
	}

	private Type resolveLocal(String trim) {
		return declarations.relative(trim)
				.map(Declaration::type)
				.orElseThrow();
	}
}

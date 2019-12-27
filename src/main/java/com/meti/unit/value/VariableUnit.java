package com.meti.unit.value;

import com.meti.Aliaser;
import com.meti.Compiler;
import com.meti.Declarations;
import com.meti.exception.CompileException;
import com.meti.type.Type;
import com.meti.type.TypeStack;
import com.meti.unit.Data;
import com.meti.unit.Declaration;
import com.meti.unit.Unit;

import java.util.Optional;

public class VariableUnit implements Unit {
	private final Aliaser aliaser;
	private final Declarations manager;
	private final TypeStack stack;

	public VariableUnit(Data data) {
		this.stack = data.getTypeStack();
		this.aliaser = data.getAliaser();
		this.manager = data.getManager();
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String name = input.trim();
		Optional<Declaration> found = manager.relativeOptionally(name);
		if (found.isPresent()) {
			Declaration declaration = found.get();
			stack.add(declaration.type());
			return declaration.isNative() ?
					Optional.of(name) :
					Optional.ofNullable(aliaser.alias(name));
		} else if (input.indexOf('.') == -1) {
			return Optional.empty();
		} else {
			int i = input.lastIndexOf('.');
			String first = input.substring(0, i);
			String last = input.substring(i + 1);
			String firstString = compiler.compile(first);
			Type firstType = stack.poll();
			if (!(firstType instanceof RecursiveType)) {
				throw new CompileException(firstType + " is not a type of object.");
			}
			Declaration parent = ((RecursiveType) firstType).parentDeclaration();
			Declaration child = parent
					.child(last)
					.orElseThrow();
			stack.add(child.type());
			return child.isNative() ?
					Optional.of(name) :
					Optional.of(firstString + "[" + parent.order(last).orElseThrow() + "]");
		}
	}

	@Override
	public Optional<Type> resolve(String input, Compiler compiler) {
		Declaration parent = manager.current();
		if (parent.name().equals(input)) {
			return Optional.of(new ObjectType(parent));
		}
		return Optional.empty();
	}

}

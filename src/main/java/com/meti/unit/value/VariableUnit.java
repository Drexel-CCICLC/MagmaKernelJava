package com.meti.unit.value;

import com.meti.Aliaser;
import com.meti.Compiler;
import com.meti.DeclareManager;
import com.meti.exception.DoesNotExistException;
import com.meti.type.TypeStack;
import com.meti.unit.Data;
import com.meti.unit.Declaration;
import com.meti.unit.Declarations;
import com.meti.unit.Unit;

import java.util.*;

public class VariableUnit implements Unit {
	private final Aliaser aliaser;
	private final Declarations declarations;
	private final DeclareManager manager;
	private final Stack<String> stack;
	private final TypeStack typeStack;

	public VariableUnit(Data data) {
		this.declarations = data.getDeclarations();
		this.aliaser = data.getAliaser();
		this.stack = data.getStack();
		typeStack = data.getTypeStack();
		manager = data.getManager();
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String name = input.trim();
		Optional<Declaration> found = manager.relativeOptionally(name);
		if (found.isPresent()) {
			return Optional.ofNullable(aliaser.alias(name));
		} else {
			if (input.indexOf('.') != -1) {
				String[] args = input.split("\\.");
				Deque<String> list = new LinkedList<>(List.of(args));
				Declaration value = manager.absolute(list);
				if (value.isNative()) {
					return Optional.of(name);
				} else {
					String child = list.removeLast();
					Declaration parent = manager.absolute(list);
					int order = parent.order(child).orElseThrow(() -> new DoesNotExistException(name + " is not " +
							"defined" +
							"."));
					return Optional.ofNullable(compiler.compile(String.join(".", list)) + "[" + order + "]");
				}
			}
		}
	}
}

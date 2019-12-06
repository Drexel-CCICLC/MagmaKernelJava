package com.meti.interpret;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MagmaInterpreter extends PatternInterpreter {
	public MagmaInterpreter() {
		this(new HashMap<>());
	}

	public MagmaInterpreter(Map<String, Type> variables) {
		this(
				Set.of(
						new AssignPattern(variables),
						new DeclarePattern(variables),
						new PrimitivePattern(),
                        new VariablePattern(),
						new IfPattern(),
						new BlockPattern()
				),
				Set.of(
						new PrimitiveResolver(),
						new VariableResolver(variables)
				)
		);
	}

	private MagmaInterpreter(Set<? extends Pattern> patterns, Set<? extends Resolver> resolvers) {
		super(patterns, resolvers);
	}
}

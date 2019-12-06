package com.meti.compile;

import com.meti.interpret.FunctionStatement;
import com.meti.interpret.Statement;
import com.meti.interpret.StatementProperty;
import com.meti.interpret.Type;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FunctionUnit implements Unit {
	@Override
	public Optional<String> translate(Statement statement, Translator translator) {
		if (statement instanceof FunctionStatement) {
			Map<String, Type> parameters = statement.getProperty(StatementProperty.PARAMETERS);
			Type returnType = statement.getProperty(StatementProperty.RETURN);
			Statement content = statement.getProperty(StatementProperty.VALUE);
			String contentString = translator.translate(content);
			String parameterString = collect(parameters);

		/*	Function<Object[], Object> function = objects -> {
				System.out.println("test0");
				System.out.println("test1");
				return null;
			};*/
			return Optional.of("(" + translator.aliaser().alias("$args") + ")->" + contentString);
		}
		return Optional.empty();
	}

	private String collect(Map<String, ? extends Type> parameters) {
		return parameters.entrySet().stream()
				.map(entry -> type(entry.getValue()) + " " + entry.getKey())
				.collect(Collectors.joining(","));
	}

	private String type(Type type) {
		return type.value().orElse("function");
	}
}

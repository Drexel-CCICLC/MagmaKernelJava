package com.meti.array;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;
import com.meti.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArrayContentParser implements Parser {
	private Optional<Node> parse(String value, Compiler compiler) {

		return Optional.empty();
	}

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		Collection<Node> toReturn = new ArrayList<>();
		String trim = value.trim();
		if (trim.startsWith("Array") && trim.contains("{")) {
			String data = trim.substring(5);
			int typeStart = data.indexOf('<') + 1;
			int typeEnd = data.indexOf('>');
			String typeString = data.substring(typeStart, typeEnd);
			Type arrayType = compiler.resolveName(typeString);
			int contentStart = data.indexOf('{') + 1;
			int contentEnd = data.indexOf('}');
			String contentString = data.substring(contentStart, contentEnd);
		}
		return toReturn;
	}
}

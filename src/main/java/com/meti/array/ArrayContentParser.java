package com.meti.array;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;

import java.util.Optional;

public class ArrayContentParser implements Parser {
	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if(trim.startsWith("Array")) {
			String data = trim.substring(5);
			int start = data.indexOf('<') + 1;
			int end = data.indexOf('>');

		}
		return Optional.empty();
	}
}

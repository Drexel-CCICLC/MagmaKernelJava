package com.meti.node.primitive.floats;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class FloatParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		try {
			String floatValue = clip(content);
			return Optional.of(new FloatNode(Float.parseFloat(floatValue)));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}

	private String clip(String content) {
		String trim = content.trim();
		return trim.endsWith("f") ?
				trim.substring(0, trim.length() - 1) :
				trim;
	}
}

package com.meti.node.primitive.doubles;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class DoubleParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		try {
			String doubleValue = clip(content);
			return Optional.of(new DoubleNode(Double.parseDouble(doubleValue)));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}

	private String clip(String content) {
		String trim = content.trim();
		return trim.endsWith("d") ?
				trim.substring(0, trim.length() - 1) :
				trim;
	}
}

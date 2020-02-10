package com.meti.node.primitive.ints;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class IntParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		try {
			String intValue = clip(content);
			return Optional.of(new IntNode(Integer.parseInt(intValue)));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}

	private String clip(String content) {
		String trim = content.trim();
		return trim.endsWith("i") ?
				trim.substring(0, trim.length() - 1) :
				trim;
	}
}

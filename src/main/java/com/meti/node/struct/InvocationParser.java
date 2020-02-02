package com.meti.node.struct;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.declare.Declaration;
import com.meti.node.declare.Declarations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InvocationParser implements Parser {
	private final Declarations declarations;

	public InvocationParser(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Node> parse(String content, Compiler compiler) throws ParseException {
		String trim = content.trim();
		if (trim.endsWith(")")) {
			int start = trim.indexOf('(');
			String caller = trim.substring(0, start);
			String values = trim.substring(start + 1, trim.length() - 1);
			Node callerNode = compiler.parse(caller);
			List<Node> arguments = new ArrayList<>();
			for (String s : values.split(",")) {
				if (!s.isBlank()) {
					Node node = compiler.parse(s);
					arguments.add(node);
				}
			}
			Optional<Declaration> optional = declarations.relative(caller);
			if (optional.isPresent()) {
				Declaration callDeclaration = optional.get();
				List<Node> parentParameters = callDeclaration.toParentParameters();
				arguments.addAll(parentParameters);
			}
			return Optional.of(new InvocationNode(callerNode, arguments));
		}
		return Optional.empty();
	}
}

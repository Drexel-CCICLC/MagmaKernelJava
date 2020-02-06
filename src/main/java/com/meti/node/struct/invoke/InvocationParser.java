package com.meti.node.struct.invoke;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.exception.ParseException;
import com.meti.exception.RenderException;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.declare.VariableNode;
import com.meti.node.struct.type.FunctionType;
import com.meti.node.struct.type.ObjectType;
import com.meti.parse.Declaration;
import com.meti.parse.Declarations;

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
			if (caller.contains(".")) {
				int period = caller.lastIndexOf(".");
				String parent = caller.substring(0, period);
				String child = caller.substring(period + 1);
				Optional<Declaration> singletonInstance = declarations.relative(parent + "$");
				if (singletonInstance.isPresent() && singletonInstance.get().child(parent).isPresent()) {
					arguments.add(new VariableNode(parent.trim()));
				}
				Optional<Declaration> objectOptional = declarations.relative(parent);
				if (objectOptional.isPresent()) {
					Type objectType = objectOptional.get().type();
					if (objectType instanceof ObjectType) {
						Declaration classDeclaration = ((ObjectType) objectType).declaration();
						if (classDeclaration.child(child).isPresent()) {
							arguments.add(new VariableNode(parent.trim()));
						} else {
							throw new ParseException(caller + " is not defined.");
						}
					} else {
						throw new ParseException(objectType + " is not an object.");
					}
				}
			}
			Optional<Declaration> optional = declarations.relative(caller);
			if (optional.isPresent()) {
				Declaration callDeclaration = optional.get();
				List<Node> parentParameters = callDeclaration.toParentParameters();
				arguments.addAll(parentParameters);
			}
			Type functionType = compiler.resolveValue(caller);
			Type returnType;
			if (functionType instanceof FunctionType) {
				returnType = ((FunctionType) functionType).returnType();
			} else {
				throw new RenderException(functionType + " is not a function.");
			}
			return Optional.of(new InvocationNode(callerNode, arguments, returnType));
		}
		return Optional.empty();
	}
}

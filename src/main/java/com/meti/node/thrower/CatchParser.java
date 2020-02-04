package com.meti.node.thrower;

import com.meti.Cache;
import com.meti.Compiler;
import com.meti.Parser;
import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.block.IfNode;
import com.meti.node.declare.Declarations;
import com.meti.node.declare.VariableNode;
import com.meti.node.primitive.NullNode;
import com.meti.node.primitive.VoidType;
import com.meti.node.struct.BlockNode;
import com.meti.node.struct.FunctionType;
import com.meti.node.struct.InvocationNode;
import com.meti.node.transform.OperationNode;
import com.meti.node.transform.Operations;

import java.util.Collections;
import java.util.Optional;

public class CatchParser implements Parser {
	private static final VariableNode THROWS = new VariableNode("_throws");
	private final Cache cache;
	private final Declarations declarations;
	private int counter = 0;

	public CatchParser(Declarations declarations, Cache cache) {
		this.declarations = declarations;
		this.cache = cache;
	}

	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.startsWith("catch")) {
			int bracketStart = trim.indexOf('{');
			String nameString = trim.substring(5, bracketStart).trim();
			if (nameString.startsWith("(") && nameString.endsWith(")")) {
				String name = nameString.substring(1, nameString.length() - 1);
				String bracketContent = trim.substring(bracketStart);
				counter++;
				Type current = declarations.current().type();
				Type returnType;
				if (current instanceof FunctionType) {
					returnType = ((FunctionType) current).returnType();
				} else {
					if (null == current) {
						returnType = VoidType.INSTANCE;
					} else {
						throw new ParseException(current + " is not a function.");
					}
				}
				compiler.parse("val _catch" + counter + "=(Any* " + name + ")=>" + returnType.toMagmaString() + ":" + bracketContent);
				return Optional.of(new IfNode(new OperationNode(THROWS, NullNode.INSTANCE, Operations.NOT_EQUALS),
						new BlockNode(
								Collections.singleton(new InvocationNode(new VariableNode("_catch" + counter),
										Collections.singletonList(THROWS), returnType))
						)));
			} else {
				throw new ParseException("Did not wrap values in parentheses.");
			}
		}
		return Optional.empty();
	}
}

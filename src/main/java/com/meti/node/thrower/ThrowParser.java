package com.meti.node.thrower;

import com.meti.Cache;
import com.meti.Compiler;
import com.meti.Parser;
import com.meti.exception.ParseException;
import com.meti.node.DefaultType;
import com.meti.node.Node;
import com.meti.node.Parameter;
import com.meti.node.Type;
import com.meti.node.declare.AssignNode;
import com.meti.node.declare.Declarations;
import com.meti.node.declare.VariableNode;
import com.meti.node.struct.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;

public class ThrowParser implements Parser {
	private final Cache cache;
	private final Declarations declarations;
	private int counter = 0;

	public ThrowParser(Declarations declarations, Cache cache) {
		this.declarations = declarations;
		this.cache = cache;
	}

	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.startsWith("throw ")) {
			String valueString = trim.substring(6);
			Type type = compiler.resolveValue(valueString);
			Node value = compiler.parse(valueString);
			Type innerType = declarations.current().type();
			if (innerType instanceof FunctionType) {
				Node assign = new AssignNode(new VariableNode("_throw"), new VariableNode("throwable"));
				FunctionType functionType = (FunctionType) innerType;
				Type returnType = functionType.returnType();
				Node toReturn;
				if (returnType instanceof DefaultType) {
					toReturn = ((DefaultType) returnType).defaultValue();
				} else {
					throw new ParseException(returnType + " has not default value.");
				}
				Node returnNode = new ReturnNode(toReturn);
				Collection<Node> children = List.of(assign, returnNode);
				counter++;
				cache.addFunction(new FunctionNode("_throw" + counter, returnType, singleton(Parameter.create(type,
						singletonList("throwable"))), new BlockNode(children)));
				return Optional.of(new ReturnNode(new InvocationNode(new VariableNode("_throw" + counter),
						singletonList(value), type)));
			}
		}
		return Optional.empty();
	}
}

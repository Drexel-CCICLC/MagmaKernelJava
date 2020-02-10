package com.meti.node.thrower;

import com.meti.Cache;
import com.meti.Compiler;
import com.meti.Parser;
import com.meti.exception.ParseException;
import com.meti.node.DefaultType;
import com.meti.node.Node;
import com.meti.node.Parameter;
import com.meti.node.Type;
import com.meti.node.block.BlockNode;
import com.meti.node.declare.AssignNode;
import com.meti.node.declare.VariableNode;
import com.meti.node.struct.FunctionNode;
import com.meti.node.struct.ReturnNode;
import com.meti.node.struct.invoke.InvocationNode;
import com.meti.node.struct.type.FunctionType;
import com.meti.parse.Declarations;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ThrowParser implements Parser {
	private static final VariableNode THROW = new VariableNode("_throw");
	private static final VariableNode THROWABLE = new VariableNode("throwable");
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
				counter++;
				buildFunction(type, (FunctionType) innerType);
				return Optional.of(buildReturn(value));
			}
		}
		return Optional.empty();
	}

	private void buildFunction(Type type, FunctionType innerType) {
		Type returnType = innerType.returnType();
		Collection<Node> children = buildChildren(returnType);
		Node content = new BlockNode(children);
		Parameter parameters = Parameter.create(type, "throwable");
		Node node = new FunctionNode("_throw" + counter, returnType, content, parameters);
		cache.addFunction(node);
	}

	private Node buildReturn(Node value) {
		Node varNode = new VariableNode("_throw" + counter);
		Node node = new InvocationNode(varNode, value);
		return new ReturnNode(node);
	}

	private Collection<Node> buildChildren(Type returnType) {
		Node assign = new AssignNode(THROW, THROWABLE);
		Node returnDefault = getReturnDefault(returnType);
		Node returnNode = new ReturnNode(returnDefault);
		return List.of(assign, returnNode);
	}

	private Node getReturnDefault(Type returnType) {
		Node toReturn;
		if (returnType instanceof DefaultType) {
			toReturn = ((DefaultType) returnType).defaultValue();
		} else {
			throw new ParseException(returnType + " has not default value.");
		}
		return toReturn;
	}
}

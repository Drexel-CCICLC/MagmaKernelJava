package com.meti.node.thrower;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.block.BlockNode;
import com.meti.node.condition.IfNode;
import com.meti.node.declare.VariableNode;
import com.meti.node.primitive.special.NullNode;
import com.meti.node.struct.invoke.InvocationNode;
import com.meti.node.struct.type.FunctionType;
import com.meti.node.transform.operate.OperationNode;
import com.meti.node.transform.operate.Operations;
import com.meti.parse.Declarations;

import java.util.Optional;

public class CatchParser implements Parser {
	private static final Node THROWS = new VariableNode("_throw");
	private final Declarations declarations;
	private int counter = 0;

	public CatchParser(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.startsWith("catch")) {
			String nameString = trim.substring(5, trim.indexOf('{')).trim();
			return parseCatch(compiler, trim, nameString);
		}
		return Optional.empty();
	}

	private Optional<Node> parseCatch(Compiler compiler, String trim, String nameString) {
		if (nameString.startsWith("(") && nameString.endsWith(")")) {
			String name = nameString.substring(1, nameString.length() - 1);
			buildNode(compiler, trim, name);
			return Optional.of(generateIf());
		} else {
			throw new ParseException("Did not wrap values in parentheses.");
		}
	}

	private void buildNode(Compiler compiler, String trim, String name) {
		counter++;
		String content = trim.substring(trim.indexOf('{'));
		Type current = declarations.current().type();
		Type returnType = buildFunctionType(current);
		generateCatch(compiler, name, content, returnType);
	}

	private Node generateIf() {
		Node condition = generateOperation();
		Node block = generateBlock();
		return new IfNode(condition, block);
	}

	private Type buildFunctionType(Type current) {
		Type returnType;
		if (current instanceof FunctionType) {
			returnType = ((FunctionType) current).returnType();
		} else {
			throw new ParseException(current + " is not a function.");
		}
		return returnType;
	}

	private void generateCatch(Compiler compiler, String name, String impl, Type returnType) {
		String catchContent = new StringBuilder()
				.append("val _catch")
				.append(counter)
				.append("=(Any* ")
				.append(name)
				.append(")=>")
				.append(returnType.toMagmaString())
				.append(":")
				.append(impl)
				.toString();
		compiler.parse(catchContent);
	}

	private Node generateOperation() {
		return new OperationNode(THROWS, Operations.NOT_EQUALS, NullNode.INSTANCE);
	}

	private Node generateBlock() {
		Node countNode = new VariableNode("_catch" + counter);
		Node invocationNode = new InvocationNode(countNode, THROWS);
		return new BlockNode(invocationNode);
	}
}
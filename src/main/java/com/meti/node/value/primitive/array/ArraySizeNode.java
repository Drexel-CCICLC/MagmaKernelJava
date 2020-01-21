package com.meti.node.value.primitive.array;

import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.value.compound.invoke.InvocationNode;
import com.meti.node.value.compound.operator.OperationNode;
import com.meti.node.value.compound.operator.Operations;
import com.meti.node.value.compound.operator.SizeOfNode;
import com.meti.node.value.compound.variable.VariableNode;

import java.util.Collections;
import java.util.LinkedList;

public class ArraySizeNode implements Node {
    private static final Node malloc = new VariableNode("malloc", false);
    private final Type type;
    private final Node size;

    public ArraySizeNode(Type type, Node size) {
        this.type = type;
        this.size = size;
    }

	@Override
	public LinkedList<Node> children() {
		return new LinkedList<>();
	}

	@Override
    public String render() {
        Node sizeOf = new SizeOfNode(type);
        Node operation = new OperationNode(size, sizeOf, Operations.MULTIPLY);
        Node invocation = new InvocationNode(malloc, Collections.singletonList(operation), false);
        return invocation.render();
    }

    @Override
    public boolean isParent() {
        return false;
    }
}

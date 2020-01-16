package com.meti.array;

import com.meti.Node;
import com.meti.Type;
import com.meti.invoke.InvocationNode;
import com.meti.operator.OperationNode;
import com.meti.operator.Operations;
import com.meti.operator.SizeOfNode;
import com.meti.other.AnyType;
import com.meti.struct.StructType;
import com.meti.variable.VariableNode;

import java.util.Collections;
import java.util.LinkedList;

public class ArraySizeNode implements Node {
    private static final Node malloc = new VariableNode("malloc");
    private static final Type mallocType = new StructType(new ArrayType(new AnyType()), "malloc", Collections.emptyList());
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
        Node invocation = new InvocationNode(mallocType, malloc, Collections.singletonList(operation));
        return invocation.render();
    }

    @Override
    public boolean isParent() {
        return false;
    }
}

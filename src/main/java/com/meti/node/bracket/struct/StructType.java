package com.meti.node.bracket.struct;

import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.other.VoidType;
import com.meti.node.value.compound.variable.FieldNodeBuilder;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class StructType implements Type {
    private final Type returnType;
    private final String name;
    private final List<? extends Type> parameters;

    public StructType(Type returnType, String name, List<? extends Type> parameters) {
        this.returnType = returnType;
        this.name = name;
        this.parameters = parameters;
    }

	@Override
	public Optional<Node> toField(Node instance, String name) {
		Optional<Type> child = childType(name.trim());
		OptionalInt order = childOrder(name.trim());
		Node field =
				new FieldNodeBuilder().withInstanceArray(instance).withOrder(order.orElseThrow()).withType(child.orElseThrow()).withName(name).build();
		return Optional.of(field);
	}

	private OptionalInt childOrder(String childName) {
		return OptionalInt.empty();
	}

	private Optional<Type> childType(String childName) {
		return Optional.empty();
	}

	@Override
    public boolean isNamed() {
        return true;
    }

    @Override
    public String render() {
        String joinedParams = parameters.stream()
                .map(Type::render)
                .collect(Collectors.joining(","));
        return returnType.render() + "(*" + name + ")" + "(" + joinedParams + ")";
    }

	@Override
	public String renderWithName(String name) {
		return (isNamed()) ? render() : render() + " " + name;
	}

	@Override
    public Optional<Type> returnType() {
        return Optional.ofNullable(returnType);
    }

	@Override
	public boolean doesReturnVoid() {
		return returnType().isPresent() && returnType().get() instanceof VoidType;
	}
}

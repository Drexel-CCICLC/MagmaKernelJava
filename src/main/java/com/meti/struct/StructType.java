package com.meti.struct;

import com.meti.Type;
import com.meti.other.VoidType;

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
	public OptionalInt childOrder(String name) {
		return OptionalInt.empty();
	}

	@Override
	public Optional<Type> childType(String name) {
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

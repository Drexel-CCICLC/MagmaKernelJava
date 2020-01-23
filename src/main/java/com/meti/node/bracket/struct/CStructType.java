package com.meti.node.bracket.struct;

import com.meti.node.Node;
import com.meti.node.Type;

import java.util.Optional;

public class CStructType implements Type {
    private final String name;

    public CStructType(String name) {
        this.name = name;
    }

    @Override
    public Optional<Type> childType(String childName) {
        return Optional.empty();
    }

	@Override
	public Optional<String> name() {
		return Optional.empty();
	}

	@Override
    public Optional<Node> toField(Node instance, String name) {
        return Optional.empty();
    }

    @Override
    public boolean doesReturnVoid() {
        return false;
    }

    @Override
    public boolean isNamed() {
        return false;
    }

    @Override
    public String render() {
        return "struct " + name;
    }

    @Override
    public String renderWithName(String name) {
        return null;
    }

    @Override
    public Optional<Type> returnType() {
        return Optional.empty();
    }
}

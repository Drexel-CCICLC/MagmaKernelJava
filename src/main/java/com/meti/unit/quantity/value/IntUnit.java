package com.meti.unit.quantity.value;

import com.meti.compile.ComplexCompiler;
import com.meti.type.Type;
import com.meti.unit.CompoundUnit;

import java.util.Optional;

public class IntUnit implements CompoundUnit {
    @Override
    public boolean canCompile(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception e) {
            return false;
        }
	}

    @Override
    public String compile(String value, ComplexCompiler compiler) {
        return value;
    }

    @Override
    public Optional<? extends Type> resolveName(String value, ComplexCompiler compiler) {
        return Optional.of("int")
                .filter(s -> value.trim().equals("int"))
                .map(IntType::new);
    }

    @Override
    public Optional<Type> resolveValue(String value, ComplexCompiler compiler) {
        return Optional.of("int")
                .filter(s -> canCompile(value.trim()))
                .map(IntType::new);
    }

    private static final class IntType implements Type {
        private final String value;

        private IntType(String value) {
            this.value = value;
        }

        @Override
        public String render() {
            return value;
        }
    }
}

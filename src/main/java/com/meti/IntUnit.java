package com.meti;

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
    public String compile(String value, Compiler compiler) {
        return value;
    }

    @Override
    public Optional<? extends Type> resolveName(String value, Compiler compiler) {
        return Optional.of("int")
                .filter(s -> value.trim().equals("int"))
                .map(IntType::new);
    }

    @Override
    public Optional<Type> resolveValue(String value, Compiler compiler) {
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

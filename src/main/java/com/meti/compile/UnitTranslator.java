package com.meti.compile;

import com.meti.interpret.Statement;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

class UnitTranslator implements Translator {
    private final Set<Unit> units;
    private final Aliaser aliaser;

    UnitTranslator(Set<Unit> units, Aliaser aliaser) {
        this.units = units;
        this.aliaser = aliaser;
    }

    @Override
    public Aliaser aliaser() {
        return aliaser;
    }

    private Optional<String> compile(com.meti.interpret.Statement statement) {
        return units.stream()
                .map(unit -> unit.translate(statement, this))
                .flatMap(Optional::stream)
                .findAny();
    }

    @Override
    public String translate(List<Statement> statements) {
        return statements.stream()
                .map(this::compile)
                .flatMap(Optional::stream)
                .collect(Collectors.joining());
    }
}

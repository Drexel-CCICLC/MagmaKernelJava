package com.meti.compile;

import com.meti.interpret.Statement;

import java.util.List;

public interface Translator {
    Aliaser aliaser();

    String translate(List<Statement> statements);
}

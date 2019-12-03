package com.meti.compile;

import java.sql.Statement;
import java.util.List;

interface Compiler {
    String compile(List<Statement> statements);
}

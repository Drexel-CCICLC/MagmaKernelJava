package com.meti.interpret;

public interface Statement {
    <T> T getProperty(StatementProperty property);
}

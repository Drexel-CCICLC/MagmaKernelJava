package com.meti.interpret;

interface Statement {
    <T> T getProperty(StatementProperty property);
}

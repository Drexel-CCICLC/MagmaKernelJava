package com.meti.compile;

import com.meti.interpret.Statement;

import java.util.Optional;

interface Unit {
    Optional<String> translate(Statement statement, Translator translator);
}

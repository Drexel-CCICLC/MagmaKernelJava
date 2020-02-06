package com.meti.core.task;

public interface Task {
    boolean canExecute(String line);

    boolean execute(String line);
}

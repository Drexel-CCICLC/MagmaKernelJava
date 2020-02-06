package com.meti.core.task;

public interface Task {
    boolean canExecute(String line);

    void execute(String line);
}

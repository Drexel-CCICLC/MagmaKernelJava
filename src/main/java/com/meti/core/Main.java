package com.meti.core;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger("compile");
    private static final List<Task> tasks = Collections.singletonList(new CompileTask(logger));

    public static void main(String[] args) {
        logger.log(Level.INFO, "Initializing compiler.");
        Scanner scanner = new Scanner(System.in);
        String line;
        do {
            line = scanner.nextLine().trim();
            for (Task task : tasks) {
                if (task.execute(line)) {
                    break;
                }
            }
        } while (!line.equals("exit"));
        logger.log(Level.INFO, "Exiting compiler.");
    }
}

package com.meti.core;

import com.meti.core.task.BuildTask;
import com.meti.core.task.CompileTask;
import com.meti.core.task.RunTask;
import com.meti.core.task.Task;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
	private static final Logger logger = Logger.getLogger("compile");
	private static final List<Task> tasks = List.of(
			new CompileTask(logger),
			new BuildTask(logger),
			new RunTask(logger));

	public static void main(String[] args) {
		logger.log(Level.INFO, "Initializing compiler.");
		try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
			String line;
			do {
				line = scanner.nextLine().trim();
				for (Task task : tasks) {
					if (task.canExecute(line)) {
						task.execute(line);
					}
				}
			} while (!line.equals("exit"));
		}
		logger.log(Level.INFO, "Exiting compiler.");
	}
}

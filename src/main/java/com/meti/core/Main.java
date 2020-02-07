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

public final class Main {
	private static final Logger logger = Logger.getLogger("compile");
	private static final List<Task> tasks = List.of(
			new CompileTask(logger),
			new BuildTask(logger),
			new RunTask(logger));

	private Main() {
	}

	public static void main(String[] args) {
		new Main().run();
	}

	private void run() {
		logger.log(Level.INFO, "Initializing compiler.");
		try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
			runCommands(scanner);
		}
		logger.log(Level.INFO, "Exiting compiler.");
	}

	private void runCommands(Scanner scanner) {
		String command;
		do {
			command = scanner.nextLine().trim();
			executeCommand(command);
		} while (!shouldExit(command));
	}

	private void executeCommand(String line) {
		tasks.stream()
				.filter(task -> task.canExecute(line))
				.forEach(task -> task.execute(line));
	}

	private boolean shouldExit(String line) {
		return "exit".equals(line);
	}
}

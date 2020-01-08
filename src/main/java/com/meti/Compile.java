package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Compile {
    public static final String EMPTY_STRING = "";
    private static final Path OUT = Paths.get("out");
    private static final MagmaCompiler compiler = new MagmaCompiler();
    private static final Set<String> headers = new HashSet<>();
    private static final Logger logger = Logger.getAnonymousLogger();

    private static void ensureFile(Path path) {
        try {
            ensureFileExceptionally(path);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to make file.", e);
        }
    }

    private static void ensureFileExceptionally(Path path) throws IOException {
        if (!Files.exists(path.getParent())) createPath(path.getParent(), true);
        if (!Files.exists(path)) createPath(path, false);
    }

    private static void createPath(Path path, boolean isDirectory) throws IOException {
        if (isDirectory) Files.createDirectories(path);
        else Files.createFile(path);
    }

    public static void main(String[] args) {
        String input = readRoot();
        String output = process(input);
        writeOutput(output);
        compileExecutable();
    }

    private static String process(String content) {
        String result = compiler.compileAll(content);
        String headerString = concatHeaders();
        return headerString + result;
    }

    private static String concatHeaders() {
        return headers.stream()
                .map(s -> "#include \"" + s + "\"\n")
                .collect(Collectors.joining());
    }

    private static String readRoot() {
        return readFromBuild(null).orElseThrow();
    }

    private static void writeOutput(String output) {
        try {
            writeOutputExceptionally(output);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to write output.", e);
        }
    }

    private static void writeOutputExceptionally(String output) throws IOException {
        Path compile = OUT.resolve("compile.c");
        ensureFile(compile);
        Files.writeString(compile, output);
    }

    private static void compileExecutable() {
        try {
            compileExecutableExceptionally();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to delete previous executable.", e);
        }
    }

    private static void compileExecutableExceptionally() throws IOException {
        Path executable = OUT.resolve("a.exe");
        Files.deleteIfExists(executable);

        runCommand("C:\\MinGW\\bin\\gcc", "compile.c");
        runCommand(executable.toAbsolutePath().toString());
    }

    private static Optional<String> readFromBuild(Path directory) {
        try {
            Path build = ensureBuildPath(directory);
            return Optional.of(readFromEnsuredBuild(directory, build));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to read build file.", e);
            return Optional.empty();
        }
    }

    private static String readFromEnsuredBuild(Path directory, Path build) throws IOException {
        return Files.readAllLines(build)
                .stream()
                .map(Paths::get)
                .map(child -> readChild(directory, child))
                .reduce((s, s2) -> s + s2)
                .orElseThrow();
    }

    private static String readChild(Path directory, Path child) {
        String toAppend = EMPTY_STRING;
        if (directory != null) child = directory.resolve(child);
        if (isHeader(child)) headers.add(child.toString());
        else toAppend = readChildContent(child).orElse(EMPTY_STRING);
        return toAppend;
    }

    private static Optional<String> readChildContent(Path child) {
        return Files.isDirectory(child) ?
                readFromBuild(child) :
                readChildLines(child);
    }

    private static Optional<String> readChildLines(Path child) {
        try {
            List<String> content = Files.readAllLines(child);
            return Optional.of(String.join(EMPTY_STRING, content));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "failed to read child.", e);
            return Optional.empty();
        }
    }

    private static boolean isHeader(Path child) {
        return child.endsWith(".h");
    }

    private static Path ensureBuildPath(Path directory) {
        Path build = Paths.get("build");
        if (directory != null) build = directory.resolve(build);
        ensureFile(build);
        return build;
    }

    private static void runCommand(String... command) {
        try {
            runCommandExceptionally(command);
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, "Failed to compile.", e);
        }
    }

    private static void runCommandExceptionally(String[] command) throws InterruptedException, IOException {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.inheritIO();
        builder.directory(OUT.toFile());
        builder.start().waitFor(1000, TimeUnit.MILLISECONDS);
    }
}

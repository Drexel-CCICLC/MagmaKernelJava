package com.meti;

import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;

class DeclareManagerTest {

	@Test
	void define() {
	}

	@Test
	void absolute() {
	}

	@Test
	void delete() {
	}

	@Test
	void relative() {
	}

	@Test
	void sink() {
		DeclareManager manager = new DeclareManager();
		manager.sink("test");
		assertDoesNotThrow(manager::surface);
	}

	@Test
	void surface() {
		DeclareManager manager = new DeclareManager();
		assertThrows(EmptyStackException.class, manager::surface);
	}
}
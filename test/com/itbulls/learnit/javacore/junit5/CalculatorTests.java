package com.itbulls.learnit.javacore.junit5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CalculatorTests {

	@Test
	void test() {
		var c = new Calculator();
		 int actual = c.add(5, 5);
		 assertEquals(10, actual);
	}

}

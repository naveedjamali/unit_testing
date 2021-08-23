package com.itbulls.learnit.javacore.junit5;

public class TestCodeCoverageDemo {
	public void testCodeCoverage(String s) {
		if(s==null || s.isEmpty()) {
			System.out.println("String is Empty");
		}
		else {
			System.out.println(s);
		}
	}

}

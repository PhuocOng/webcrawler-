package com.example.webcrawler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class TestingExampleTests {
    private TestingExample objectExample;

    @BeforeEach
    public void setup() {
        objectExample = new TestingExample();
    }

    @RepeatedTest(value = 3)
    @DisplayName("Test multiply_three 123S")
    void testMultiply_three() {
        assertEquals(objectExample.multiply_three(9), 27);
        assertEquals(objectExample.multiply_three(10), 30);
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Just finished a test"); // Corrected the print statement
    }
}

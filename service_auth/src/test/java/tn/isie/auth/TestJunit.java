package tn.isie.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestJunit {

    @Test
    void testAddition() {
        int a = 2;
        int b = 3;

        int result = a + b;

        assertEquals(5, result);
    }

    @Test
    void testBooleanCondition() {
        boolean isAuthenticated = true;

        assertTrue(isAuthenticated);
    }
}

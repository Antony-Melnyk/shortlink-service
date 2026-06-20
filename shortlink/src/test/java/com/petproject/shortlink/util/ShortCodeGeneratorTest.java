package com.petproject.shortlink.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortCodeGeneratorTest {

    private final ShortCodeGenerator generator = new ShortCodeGenerator();

    @Test
    void shouldGenerateCodeWithLengthSix() {
        String code = generator.generate();

        assertEquals(6, code.length());
    }

    @Test
    void shouldGenerateOnlyAllowedCharacters() {
        String code = generator.generate();

        assertTrue(code.matches("[a-zA-Z0-9]+"));
    }
}
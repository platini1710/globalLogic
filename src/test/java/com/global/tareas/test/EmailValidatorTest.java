package com.global.tareas.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.global.tareas.helper.EmailValidatorSimple;

public class EmailValidatorTest {
	  @ParameterizedTest(name = "#{index} - Run test with email = {0}")
	    @MethodSource("validEmailProvider")
	    void test_email_valid(String email) {
	        assertTrue(EmailValidatorSimple.isValid(email));
	    }

	    @ParameterizedTest(name = "#{index} - Run test with email = {0}")
	    @MethodSource("invalidEmailProvider")
	    void test_email_invalid(String email) {
	        assertFalse(EmailValidatorSimple.isValid(email));
	    }

	    // Valid email addresses
	    static Stream<String> validEmailProvider() {
	        return Stream.of(
	                "hello@example.com",                // simple
	                "hello@example.co.uk",              // .co.uk
	                "hello-.+_=#|@example.com",         // special characters
	                "h@example.com",                    // local-part one letter
	                "h@com",                            // domain one letter
	                "aespinoza@gmail.com"                      // unicode, chinese characters
	        );
	    }

	    // Invalid email addresses
	    static Stream<String> invalidEmailProvider() {
	        return Stream.of(
	                "hello",                            // email need at least one @
	                "hello@ "                           // domain cant end with space (whitespace)
	        );
	    }

	
}

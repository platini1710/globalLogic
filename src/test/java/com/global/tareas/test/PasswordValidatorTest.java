package com.global.tareas.test;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import com.global.tareas.helper.PasswordValidator;

import junitparams.JUnitParamsRunner;



public class PasswordValidatorTest {

    @ParameterizedTest
    @MethodSource("validPasswordProvider")
    public void test_password_regex_valid(String password) {
    	assertTrue(PasswordValidator.isValid(password));
       
    }

    @ParameterizedTest(name = "#{index} - Run test with password = {0}")
    @MethodSource("invalidPasswordProvider")
    void test_password_regex_invalid(String password) {
    	assertTrue(PasswordValidator.isValid(password));
    }

     static Stream<String> validPasswordProvider() {
        return Stream.of(
                "A23bbbccc@",
                "Hello word12",
                "A!@#2&()aa1",              // test punctuation part 1
                "A[{23}]:;a",           // test punctuation part 2
                "A7$^+=2a#",               // test symbols
                "A89$abcdeab",     // test 20 chars
                "12fAa$wa"                  // test 8 chars
        );
    }

    // At least
    // one lowercase character,
    // one uppercase character,
    // one digit,
    // one special character
    // and length between 8 to 20.
    static Stream<String> invalidPasswordProvider() {
        return Stream.of(
                "12345678",                 // invalid, only digit
                "abcdefgh",                 // invalid, only lowercase
                "ABCDEFGH",                 // invalid, only uppercase
                "abc123$$$",                // invalid, at least one uppercase
                "ABC123$$$",                // invalid, at least one lowercase
                "ABC$$$$$$",                // invalid, at least one digit
                "java REGEX 123",           // invalid, at least one special character
                "java REGEX 123 %",         // invalid, % is not in the special character group []
                "________",                 // invalid
                "--------",                 // invalid
                " ",                        // empty
                "Asds23");                        // empty
    }
}
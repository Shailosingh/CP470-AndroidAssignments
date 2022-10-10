package com.example.androidassignments;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginActivityUnitTest
{
    @Test
    public void IsEmailValid()
    {
        assertFalse(LoginActivity.IsEmailValid("bloop"));           //Testing to see that it fails on email without @ or .
        assertFalse(LoginActivity.IsEmailValid("bloop.com"));       //Testing to see that it fails on email without @
        assertFalse(LoginActivity.IsEmailValid("bloop@bleepcom"));  //Testing to see that it fails on email without .
        assertFalse(LoginActivity.IsEmailValid(null));              //Testing to see that it fails on null
        assertFalse(LoginActivity.IsEmailValid(""));                //Testing to see that it fails on empty string


        assertTrue(LoginActivity.IsEmailValid("bloop@bleep.com"));
        assertTrue(LoginActivity.IsEmailValid("sing7779@mylaurier.ca"));
    }
}

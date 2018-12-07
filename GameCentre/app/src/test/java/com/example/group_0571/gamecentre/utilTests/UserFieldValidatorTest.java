package com.example.group_0571.gamecentre.utilTests;

import android.text.Editable;
import android.widget.EditText;

import com.example.group_0571.gamecentre.GamecentreMockContext;
import com.example.group_0571.gamecentre.utils.UserFieldValidator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the UserFieldValidator class.
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class UserFieldValidatorTest {
    /**
     * A MockContext used for testing.
     */
    @Mock
    private GamecentreMockContext context;

    /**
     * Editables that will be returned by EditTexts for testing.
     */
    @Mock
    private Editable usernameEditable, passwordEditable, confirmPasswordEditable;

    /**
     * EditText fields used for testing.
     */
    @Mock
    private EditText usernameField, passwordField, confirmPasswordField;

    /**
     * The UserFieldValidator used for testing.
     */
    private UserFieldValidator validator;

    /**
     * Ensure various mock methods return dummy values.
     */
    @Before
    public void setUp() {
        List<EditText> fields = new ArrayList<>();
        fields.add(usernameField);
        fields.add(passwordField);
        fields.add(confirmPasswordField);
        validator = new UserFieldValidator(fields);
        when(context.getString(anyInt())).thenReturn("");
        when(usernameField.getText()).thenReturn(usernameEditable);
        when(passwordField.getText()).thenReturn(passwordEditable);
        when(confirmPasswordField.getText()).thenReturn(confirmPasswordEditable);
    }

    /**
     * Test whether validateSignInFields correctly returns if the fields are valid.
     */
    @Test
    public void testValidateSignInFieldsValid() {
        when(usernameEditable.toString()).thenReturn("username");
        when(passwordEditable.toString()).thenReturn("password");

        assertTrue(validator.validateSignInFields(context));
    }

    /**
     * Test whether validateSignInFields correctly returns if the fields are invalid.
     */
    @Test
    public void testValidateSignInFieldsInvalid() {
        when(usernameEditable.toString()).thenReturn(" ");
        when(passwordEditable.toString()).thenReturn(" ");

        assertFalse(validator.validateSignInFields(context));
    }

    /**
     * Test whether validateSignUpFields correctly returns if the fields are valid.
     */
    @Test
    public void testValidateSignUpFieldsValid() {
        when(usernameEditable.toString()).thenReturn("username");
        when(passwordEditable.toString()).thenReturn("password");
        when(confirmPasswordEditable.toString()).thenReturn("password");

        assertTrue(validator.validateSignUpFields(context, new HashSet<String>()));
    }

    /**
     * Test whether validateSignUpFields correctly returns if the fields are valid but the username
     * already exists.
     */
    @Test
    public void testValidateSignUpFieldsValidUserExists() {
        when(usernameEditable.toString()).thenReturn("username");
        when(passwordEditable.toString()).thenReturn("password");
        when(confirmPasswordEditable.toString()).thenReturn("password");

        Set<String> users = new HashSet<>();
        users.add("username");

        assertFalse(validator.validateSignUpFields(context, users));
    }

    /**
     * Test whether validateSignUpFields correctly returns if the fields are invalid.
     */
    @Test
    public void testValidateSignUpFieldsInvalid() {
        when(usernameEditable.toString()).thenReturn(" ");
        when(passwordEditable.toString()).thenReturn(" ");
        when(confirmPasswordEditable.toString()).thenReturn("password1");

        assertFalse(validator.validateSignUpFields(context, new HashSet<String>()));

        when(usernameEditable.toString()).thenReturn("user");
        assertFalse(validator.validateSignUpFields(context, new HashSet<String>()));
    }

    /**
     * Test whether validateSignUpFields correctly returns if the passwords don't match.
     */
    @Test
    public void testValidateSignUpFieldsPasswordNotConfirmed() {
        when(usernameEditable.toString()).thenReturn("username");
        when(passwordEditable.toString()).thenReturn("password");
        when(confirmPasswordEditable.toString()).thenReturn("password1");

        assertFalse(validator.validateSignUpFields(context, new HashSet<String>()));
    }
}

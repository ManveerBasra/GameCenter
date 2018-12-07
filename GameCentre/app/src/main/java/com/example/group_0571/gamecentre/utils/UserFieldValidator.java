package com.example.group_0571.gamecentre.utils;

import android.content.Context;
import android.widget.EditText;

import com.example.group_0571.gamecentre.R;

import java.util.List;
import java.util.Set;

/**
 * Validates fields for sign in and sign up.
 */
public class UserFieldValidator {

    /**
     * Username field
     */
    private EditText usernameField;

    /**
     * Password field
     */
    private EditText passwordField;

    /**
     * Confirm password field
     */
    private EditText confirmPasswordField;

    /**
     * Initialize the fields into the class for ease of access
     * Precondition: fields.size() >= 2
     *
     * @param fields the List of EditTexts
     */
    public UserFieldValidator(List<EditText> fields) {
        usernameField = fields.get(0);
        passwordField = fields.get(1);
        if (fields.size() > 2)
            confirmPasswordField = fields.get(2);
    }

    /**
     * Validate sign in fields.
     *
     * @param context the Context from which the fields are being validated
     * @return whether the fields have valid values
     */
    public boolean validateSignInFields(Context context) {
        String username = usernameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        usernameField.setError(username.length() == 0 ?
                context.getString(R.string.is_required, context.getString(R.string.username)) : null);
        passwordField.setError(password.length() == 0 ?
                context.getString(R.string.is_required, context.getString(R.string.password)) : null);
        return username.length() > 0 && password.length() > 0;
    }

    /**
     * Validate the sign up fields.
     *
     * @param context      the Context from which the fields are being validated
     * @param currentUsers a Set of current users
     * @return whether the fields have valid values
     */
    public boolean validateSignUpFields(Context context, Set<String> currentUsers) {
        return checkUsername(context, currentUsers) && checkPassword(context);
    }

    /**
     * Validates a username based on its length and current registered users.
     *
     * @param context      to getString for resources
     * @param currentUsers to compare username against
     * @return true if the username is valid
     */
    private boolean checkUsername(Context context, Set<String> currentUsers) {
        String username = usernameField.getText().toString().trim();

        if (username.length() == 0) {
            usernameField.setError(context.getString(R.string.is_required,
                    context.getString(R.string.username)));
        } else if (currentUsers.contains(username)) {
            usernameField.setError(context.getString(R.string.username_exists));
        } else {
            usernameField.setError(null);
        }
        return username.length() > 0 && (!currentUsers.contains(username));
    }

    /**
     * Validates a password based on its length and equivalence to confirmPassword.
     *
     * @param context to getString for resources
     * @return true if the username is valid
     */
    private boolean checkPassword(Context context) {
        String password = passwordField.getText().toString().trim();
        String confirmPassword = confirmPasswordField.getText().toString().trim();
        if (password.length() == 0) {
            passwordField.setError(context.getString(R.string.is_required, context.getString(R.string.password)));
        } else if (!password.equals(confirmPassword)) {
            confirmPasswordField.setError(context.getString(R.string.passwords_must_match));
        } else {
            passwordField.setError(null);
            confirmPasswordField.setError(null);
        }
        return password.length() > 0 && confirmPassword.equals(password);
    }
}

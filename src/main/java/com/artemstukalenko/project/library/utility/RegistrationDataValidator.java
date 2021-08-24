package com.artemstukalenko.project.library.utility;

import static com.artemstukalenko.project.library.utility.RegexContainer.*;

public class RegistrationDataValidator {

    public boolean dataIsValid(String username, String firstName, String lastName, String email,
                               String phoneNumber, String address) {

        if (username.matches(VALID_USERNAME) && firstName.matches(VALID_NAME)
                && lastName.matches(VALID_SURNAME)
                && email.matches(VALID_EMAIL)
                && phoneNumber.matches(VALID_PHONE_NUMBER)
                && !address.isEmpty()) {
            return true;
        }

        return false;
    }

}

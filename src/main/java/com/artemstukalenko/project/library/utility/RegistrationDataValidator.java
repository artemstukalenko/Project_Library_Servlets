package com.artemstukalenko.project.library.utility;

import java.util.ArrayList;
import java.util.List;

import static com.artemstukalenko.project.library.utility.RegexContainer.*;

public class RegistrationDataValidator {

    public List<String> getFieldsWithMistakes(String username, String firstName, String lastName, String email,
                                              String phoneNumber) {

        List<String> mistakes = new ArrayList<>();

        if (!username.matches(VALID_USERNAME)) {
            mistakes.add("username");
        }
        if(!firstName.matches(VALID_NAME)) {
            mistakes.add("firstName");
        }
        if(!lastName.matches(VALID_SURNAME)) {
            mistakes.add("lastName");
        }
        if(!email.matches(VALID_EMAIL)) {
            mistakes.add("email");
        }
        if(!phoneNumber.matches(VALID_PHONE_NUMBER)) {
            mistakes.add("phoneNumber");
        }

        return mistakes;
    }

}

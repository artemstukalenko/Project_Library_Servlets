package com.artemstukalenko.project.library.utility;

public interface RegexContainer {
    String VALID_SURNAME = "[A-Z]{1}[a-z]{1,}([-]|[ ])?(([A-Z]{1})?+[a-z]+)?";
    String VALID_SURNAME_UA = "[А-ЩЬЮЯҐІЇЄ]{1}[а-щьюяґіїє']{1,}([-]|[ ])?(([А-ЩЬЮЯҐІЇЄ]{1})?+[а-щьюяґіїє']+)?";
    String VALID_NAME = "[A-Z]{1}[a-z]+([-]|[ ])?(([A-Z]{1})?+[a-z]+)?";
    String VALID_MIDDLE_NAME = "[A-Z]{1}[a-z]{4,}";
    String VALID_USERNAME = "[a-z]+[\\w]{4,15}";
    String VALID_PHONE_NUMBER = "(([+][3][8])?[0])[0-9]{9}";
    String VALID_EMAIL = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$";
    String VALID_POSTAL_CODE = "[0-9]{5}";
    String VALID_CITY = "[A-Z]{1}[a-z]+([-]|[ ])?([A-Z]{1}[a-z]+)?";
    String VALID_STREET = "[A-Z]{1}[a-z]+([-]|[ ])?([A-Z]{1}?[a-z]+)?([ ])?([A-Z]{1}?[a-z]+)?";
    String VALID_BUILDING_NUMBER = "[1-9]([0-9]{0,3})?([/]|[-])?[1-9]([0-9]{0,3})?[ ]?([A-Za-z]{1})?";
    String VALID_APARTMENT_NUMBER = "[1-9][0-9]{1,2}?";
}

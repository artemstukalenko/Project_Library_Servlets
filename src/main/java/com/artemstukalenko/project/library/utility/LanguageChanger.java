package com.artemstukalenko.project.library.utility;

import com.artemstukalenko.project.library.view.FirstView;

public class LanguageChanger {

    private static FirstView textInfo = new FirstView();

    private static String currentLanguage;

    public static void changeLanguage(String desiredLanguage) {

        if (currentLanguage == null) {
            if (desiredLanguage == null) {
                currentLanguage = desiredLanguage = "en";
            }
        }
        if (desiredLanguage != null) {
            if (!desiredLanguage.equals(currentLanguage)) {
                currentLanguage = desiredLanguage;
            }
        }

        switch (currentLanguage) {
            case "en":
                textInfo.changeLanguageToEn();
                break;
            case "ua":
                textInfo.changeLanguageToUa();
                break;
            default:
                textInfo.changeLanguageToEn();
                break;
        }
    }

}

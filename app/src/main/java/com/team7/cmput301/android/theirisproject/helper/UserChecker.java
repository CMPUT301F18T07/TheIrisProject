package com.team7.cmput301.android.theirisproject.helper;

import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.User;
import com.team7.cmput301.android.theirisproject.model.User.UserType;

public class UserChecker {
    public static Class<? extends User> getUserType(String type) {
        if (type.equals(UserType.PATIENT.toString())) {
            return Patient.class;
        } else {
            return CareProvider.class;
        }
    }

}

package com.app.user.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class ArgonUtil {

    private static final Argon2 ARGON_2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    private ArgonUtil() {
    }

    public static String hashPassword(CharSequence rawPassword) {
        return ARGON_2.hash(4, 12000, 8, rawPassword.toString());
    }

    public static boolean matchesUserPassword(CharSequence rawPassword, String encodedPassword) {
        return ARGON_2.verify(encodedPassword, rawPassword.toString());
    }
}

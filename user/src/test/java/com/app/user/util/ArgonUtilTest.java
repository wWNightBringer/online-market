package com.app.user.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

class ArgonUtilTest {

    @Test
    void test_hashPassword() {
        String password = "admin";
        String hashPassword = ArgonUtil.hashPassword(password);
        Assertions.assertTrue(Objects.nonNull(hashPassword));
    }
}

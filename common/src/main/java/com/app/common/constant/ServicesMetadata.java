package com.app.common.constant;

public final class ServicesMetadata {
    public static final String USER_SERVICE_URL = "http://localhost:9001";
    public static final String STORAGE_SERVICE_URL = "http://localhost:9003";

    public static final String USER_SERVICE_URL_GET_USER_ID = "/api/v1/users/id/%s";

    private ServicesMetadata() {
    }
}

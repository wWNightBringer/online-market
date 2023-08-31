package com.app.common.dto;

import java.util.List;

public record ResponsePage<T>(List<T> content, long totalElements) {
}

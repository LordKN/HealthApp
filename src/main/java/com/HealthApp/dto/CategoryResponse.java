package com.HealthApp.dto;

import java.util.List;

public record CategoryResponse(int count,
                               String next,
                               String previous,
                               List<WgerCategoryDto> results) {
}

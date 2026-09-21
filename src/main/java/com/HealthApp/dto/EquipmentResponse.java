package com.HealthApp.dto;

import java.util.List;

public record EquipmentResponse(int count,
                                String next,
                                String previous,
                                List<WgerEquipmentDto> results) {
}

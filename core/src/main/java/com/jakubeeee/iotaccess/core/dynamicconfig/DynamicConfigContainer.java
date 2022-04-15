package com.jakubeeee.iotaccess.core.dynamicconfig;

import lombok.NonNull;

import java.util.List;

public record DynamicConfigContainer(@NonNull List<DynamicConfigEntry> entries) {
}

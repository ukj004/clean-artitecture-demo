package com.ujjwal.cleanartitecturedemo;

import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "persistence")
public record PersistenceProperties(
        @NonNull String mongoConnectionString
) {
}

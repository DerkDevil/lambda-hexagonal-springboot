package com.example.demo.helpers;

import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class EnvHelper {

    public String require(String key) {
        return Optional.ofNullable(System.getenv(key))
                .orElseThrow(() -> new IllegalStateException("Missing env var: " + key));
    }
}

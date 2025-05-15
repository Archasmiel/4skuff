package net.archasmiel.skufapi.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UUIDGenerator {

    public String username() {
        return "user_" + fromUUID2x();
    }

    public String password() {
        return fromUUID2x();
    }

    public String fromUUID1x() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public String fromUUID2x() {
        return fromUUID1x() + fromUUID1x();
    }

    public String fromUUID4x() {
        return fromUUID2x() + fromUUID2x();
    }

}

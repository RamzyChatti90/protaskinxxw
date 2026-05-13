package com.example.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AppUserTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AppUser getAppUserSample1() {
        return new AppUser().id(1L).firstName("firstName1").lastName("lastName1").avatarUrl("avatarUrl1").phone("phone1");
    }

    public static AppUser getAppUserSample2() {
        return new AppUser().id(2L).firstName("firstName2").lastName("lastName2").avatarUrl("avatarUrl2").phone("phone2");
    }

    public static AppUser getAppUserRandomSampleGenerator() {
        return new AppUser()
            .id(longCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .avatarUrl(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString());
    }
}

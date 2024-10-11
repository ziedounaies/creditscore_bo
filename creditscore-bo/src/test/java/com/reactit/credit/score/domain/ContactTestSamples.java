package com.reactit.credit.score.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ContactTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Contact getContactSample1() {
        return new Contact().id(1L).value("value1");
    }

    public static Contact getContactSample2() {
        return new Contact().id(2L).value("value2");
    }

    public static Contact getContactRandomSampleGenerator() {
        return new Contact().id(longCount.incrementAndGet()).value(UUID.randomUUID().toString());
    }
}

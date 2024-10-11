package com.reactit.credit.score.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ClaimTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Claim getClaimSample1() {
        return new Claim().id(1L).title("title1").message("message1");
    }

    public static Claim getClaimSample2() {
        return new Claim().id(2L).title("title2").message("message2");
    }

    public static Claim getClaimRandomSampleGenerator() {
        return new Claim().id(longCount.incrementAndGet()).title(UUID.randomUUID().toString()).message(UUID.randomUUID().toString());
    }
}

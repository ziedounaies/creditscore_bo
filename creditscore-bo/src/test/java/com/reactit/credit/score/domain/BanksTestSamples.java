package com.reactit.credit.score.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BanksTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Banks getBanksSample1() {
        return new Banks().id(1L).name("name1").branches("branches1");
    }

    public static Banks getBanksSample2() {
        return new Banks().id(2L).name("name2").branches("branches2");
    }

    public static Banks getBanksRandomSampleGenerator() {
        return new Banks().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).branches(UUID.randomUUID().toString());
    }
}

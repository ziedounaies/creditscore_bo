package com.reactit.credit.score.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AgenciesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Agencies getAgenciesSample1() {
        return new Agencies().id(1L).name("name1").datefounded("datefounded1");
    }

    public static Agencies getAgenciesSample2() {
        return new Agencies().id(2L).name("name2").datefounded("datefounded2");
    }

    public static Agencies getAgenciesRandomSampleGenerator() {
        return new Agencies().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).datefounded(UUID.randomUUID().toString());
    }
}

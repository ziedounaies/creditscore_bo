package com.reactit.credit.score.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CreditRapportTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CreditRapport getCreditRapportSample1() {
        return new CreditRapport()
            .id(1L)
            .creditScore("creditScore1")
            .accountAge("accountAge1")
            .creditLimit("creditLimit1")
            .inquiriesAndRequests("inquiriesAndRequests1");
    }

    public static CreditRapport getCreditRapportSample2() {
        return new CreditRapport()
            .id(2L)
            .creditScore("creditScore2")
            .accountAge("accountAge2")
            .creditLimit("creditLimit2")
            .inquiriesAndRequests("inquiriesAndRequests2");
    }

    public static CreditRapport getCreditRapportRandomSampleGenerator() {
        return new CreditRapport()
            .id(longCount.incrementAndGet())
            .creditScore(UUID.randomUUID().toString())
            .accountAge(UUID.randomUUID().toString())
            .creditLimit(UUID.randomUUID().toString())
            .inquiriesAndRequests(UUID.randomUUID().toString());
    }
}

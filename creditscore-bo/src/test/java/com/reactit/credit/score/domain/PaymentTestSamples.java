package com.reactit.credit.score.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PaymentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Payment getPaymentSample1() {
        return new Payment()
            .id(1L)
            .checkNumber("checkNumber1")
            .checkIssuer("checkIssuer1")
            .accountNumber("accountNumber1")
            .recipient("recipient1")
            .amount("amount1")
            .currency("currency1");
    }

    public static Payment getPaymentSample2() {
        return new Payment()
            .id(2L)
            .checkNumber("checkNumber2")
            .checkIssuer("checkIssuer2")
            .accountNumber("accountNumber2")
            .recipient("recipient2")
            .amount("amount2")
            .currency("currency2");
    }

    public static Payment getPaymentRandomSampleGenerator() {
        return new Payment()
            .id(longCount.incrementAndGet())
            .checkNumber(UUID.randomUUID().toString())
            .checkIssuer(UUID.randomUUID().toString())
            .accountNumber(UUID.randomUUID().toString())
            .recipient(UUID.randomUUID().toString())
            .amount(UUID.randomUUID().toString())
            .currency(UUID.randomUUID().toString());
    }
}

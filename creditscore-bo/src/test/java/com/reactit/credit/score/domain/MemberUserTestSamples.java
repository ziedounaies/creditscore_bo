package com.reactit.credit.score.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MemberUserTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MemberUser getMemberUserSample1() {
        return new MemberUser()
            .id(1L)
            .userName("userName1")
            .firstName("firstName1")
            .lastName("lastName1")
            .businessName("businessName1")
            .identifierValue("identifierValue1")
            .employersReported("employersReported1")
            .income("income1")
            .expenses("expenses1")
            .grossProfit("grossProfit1")
            .netProfitMargin("netProfitMargin1")
            .debtsObligations("debtsObligations1");
    }

    public static MemberUser getMemberUserSample2() {
        return new MemberUser()
            .id(2L)
            .userName("userName2")
            .firstName("firstName2")
            .lastName("lastName2")
            .businessName("businessName2")
            .identifierValue("identifierValue2")
            .employersReported("employersReported2")
            .income("income2")
            .expenses("expenses2")
            .grossProfit("grossProfit2")
            .netProfitMargin("netProfitMargin2")
            .debtsObligations("debtsObligations2");
    }

    public static MemberUser getMemberUserRandomSampleGenerator() {
        return new MemberUser()
            .id(longCount.incrementAndGet())
            .userName(UUID.randomUUID().toString())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .businessName(UUID.randomUUID().toString())
            .identifierValue(UUID.randomUUID().toString())
            .employersReported(UUID.randomUUID().toString())
            .income(UUID.randomUUID().toString())
            .expenses(UUID.randomUUID().toString())
            .grossProfit(UUID.randomUUID().toString())
            .netProfitMargin(UUID.randomUUID().toString())
            .debtsObligations(UUID.randomUUID().toString());
    }
}

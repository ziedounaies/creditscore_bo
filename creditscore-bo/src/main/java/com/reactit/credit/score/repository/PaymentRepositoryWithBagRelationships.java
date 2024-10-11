package com.reactit.credit.score.repository;

import com.reactit.credit.score.domain.Payment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PaymentRepositoryWithBagRelationships {
    Optional<Payment> fetchBagRelationships(Optional<Payment> payment);

    List<Payment> fetchBagRelationships(List<Payment> payments);

    Page<Payment> fetchBagRelationships(Page<Payment> payments);
}

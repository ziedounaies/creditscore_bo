package com.reactit.credit.score.repository;

import com.reactit.credit.score.domain.Payment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PaymentRepositoryWithBagRelationshipsImpl implements PaymentRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Payment> fetchBagRelationships(Optional<Payment> payment) {
        return payment.map(this::fetchProducts);
    }

    @Override
    public Page<Payment> fetchBagRelationships(Page<Payment> payments) {
        return new PageImpl<>(fetchBagRelationships(payments.getContent()), payments.getPageable(), payments.getTotalElements());
    }

    @Override
    public List<Payment> fetchBagRelationships(List<Payment> payments) {
        return Optional.of(payments).map(this::fetchProducts).orElse(Collections.emptyList());
    }

    Payment fetchProducts(Payment result) {
        return entityManager
            .createQuery("select payment from Payment payment left join fetch payment.products where payment.id = :id", Payment.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Payment> fetchProducts(List<Payment> payments) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, payments.size()).forEach(index -> order.put(payments.get(index).getId(), index));
        List<Payment> result = entityManager
            .createQuery("select payment from Payment payment left join fetch payment.products where payment in :payments", Payment.class)
            .setParameter("payments", payments)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}

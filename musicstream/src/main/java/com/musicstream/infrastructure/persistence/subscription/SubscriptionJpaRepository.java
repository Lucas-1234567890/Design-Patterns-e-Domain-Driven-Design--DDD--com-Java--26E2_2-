package com.musicstream.infrastructure.persistence.subscription;

import com.musicstream.domain.subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionJpaRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByAccountIdAndActiveTrue(Long accountId);
    boolean existsByAccountIdAndActiveTrue(Long accountId);
}

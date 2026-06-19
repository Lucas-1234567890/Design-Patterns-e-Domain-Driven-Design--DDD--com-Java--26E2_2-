package com.musicstream.infrastructure.persistence.subscription;

import com.musicstream.domain.subscription.model.Subscription;
import com.musicstream.domain.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final SubscriptionJpaRepository jpaRepository;

    @Override
    public Subscription save(Subscription subscription) {
        return jpaRepository.save(subscription);
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Subscription> findActiveSubscriptionByAccountId(Long accountId) {
        return jpaRepository.findByAccountIdAndActiveTrue(accountId);
    }

    @Override
    public boolean hasActiveSubscription(Long accountId) {
        return jpaRepository.existsByAccountIdAndActiveTrue(accountId);
    }
}

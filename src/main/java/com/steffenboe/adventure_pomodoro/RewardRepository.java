package com.steffenboe.adventure_pomodoro;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RewardRepository extends ReactiveMongoRepository<Reward, String> {

}


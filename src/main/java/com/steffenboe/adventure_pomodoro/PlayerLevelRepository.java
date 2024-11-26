package com.steffenboe.adventure_pomodoro;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PlayerLevelRepository extends ReactiveMongoRepository<PlayerLevel, String> {
    
}

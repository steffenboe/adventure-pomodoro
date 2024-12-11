package com.steffenboe.adventure_pomodoro;

import java.util.Date;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DailyGenerationRepository extends ReactiveMongoRepository<DailyGeneration, String> {
    
}

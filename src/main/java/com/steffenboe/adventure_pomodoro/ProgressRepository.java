package com.steffenboe.adventure_pomodoro;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProgressRepository extends ReactiveMongoRepository<Progress, String> {

}

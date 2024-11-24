package com.steffenboe.adventure_pomodoro;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

interface TodoRepository extends ReactiveMongoRepository<Todo, String> {

}

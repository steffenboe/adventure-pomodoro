package com.steffenboe.adventure_pomodoro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import org.checkerframework.checker.units.qual.t;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/todos")
public class TodoListController {

    private final TodoRepository todoRepository;

    public TodoListController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @PostConstruct
    void setupTodos() {
        todoRepository.findAll().switchIfEmpty(todoRepository
                .save(new Todo(UUID.randomUUID().toString(), "Test Todo", false, null, Collections.emptyList())))
                .subscribe();
    }

    @PostMapping
    public Mono<ResponseEntity<Todo>> createTodo(@RequestBody Todo todo) {
        todo.setId(UUID.randomUUID().toString());
        return todoRepository.save(todo)
                .map(t -> {
                    System.out.println("New Todo added!");
                    return ResponseEntity.ok().body(t);
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @GetMapping
    public Flux<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Object>> completeTodo(@PathVariable String id, @RequestBody Todo update) {
        return todoRepository.findById(id)
                .flatMap(todo -> {
                    todo.setCompleted(update.isCompleted());
                    todo.setNotes(update.getNotes());
                    return todoRepository.save(todo)
                            .thenReturn(ResponseEntity.ok().build());
                })
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest()
                        .body("Todo not found")));
    }

    @PutMapping("/clearLabels")
    public Mono<ResponseEntity<Void>> clearLabels() {
        return todoRepository.findAll()
                .flatMap(todo -> {
                    todo.setLabels(new ArrayList<>());
                    return todoRepository.save(todo);
                })
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> deleteTodo(@PathVariable String id) {
        return todoRepository.deleteById(id)
                .thenReturn(ResponseEntity.noContent().build())
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }
}

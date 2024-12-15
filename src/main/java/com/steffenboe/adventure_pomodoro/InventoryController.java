package com.steffenboe.adventure_pomodoro;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = { "http://localhost:8080/**", "http://localhost:3000" })
public class InventoryController {

    private final InventoryRepository inventoryRepository;
    private final ItemRepository itemRepository;
    private final TodoRepository todoRepository;

    public InventoryController(InventoryRepository inventoryRepository, ItemRepository itemRepository,
            TodoRepository todoRepository) {
        this.inventoryRepository = inventoryRepository;
        this.itemRepository = itemRepository;
        this.todoRepository = todoRepository;
    }

    @PostConstruct
    void setupInventory() {
        inventoryRepository.findById("1")
                .switchIfEmpty(inventoryRepository.save(new Inventory("1", new HashSet<>())))
                .subscribe();
    }

    @GetMapping
    Mono<ResponseEntity<Inventory>> getInventory() {
        return inventoryRepository.findById("1")
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/{itemId}")
    Mono<ResponseEntity<Inventory>> addItem(@PathVariable String itemId) {
        return inventoryRepository.findById("1")
                .flatMap(inventory -> itemRepository.findById(itemId)
                        .map(item -> inventory.addItem(item))
                        .flatMap(i -> inventoryRepository.save(i)))
                .flatMap(inventory -> inventoryRepository.save(inventory)
                        .map(ResponseEntity::ok)
                        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build())));
    }

    @PutMapping("/items/{itemId}/use")
    Mono<ResponseEntity<Inventory>> useItem(@PathVariable String itemId) {
        return inventoryRepository.findById("1")
                .map(inventory -> {
                    inventory.getItems().stream().filter(item -> item.id().equals(itemId)).findFirst()
                            .ifPresent(item -> {
                                todoRepository
                                        .save(new Todo(UUID.randomUUID().toString(), item.name(), false, "",
                                                List.of("New!")))
                                        .subscribe();
                            });
                    return inventory.removeItem(itemId);
                })
                .flatMap(inventory -> {
                    return inventoryRepository.save(inventory)
                            .map(ResponseEntity::ok)
                            .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
                });
    }

    @GetMapping("/equipment")
    Mono<ResponseEntity<Item>> getEquipment() {
        return Mono.just(ResponseEntity.ok().build());
    }
}

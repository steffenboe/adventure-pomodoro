package com.steffenboe.adventure_pomodoro;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/marketplace")
public class MarketplaceController {

    private final ItemRepository itemRepository;
    private final RewardRepository rewardRepository;

    MarketplaceController(ItemRepository itemRepository, RewardRepository rewardRepository) {
        this.itemRepository = itemRepository;
        this.rewardRepository = rewardRepository;
    }

    @GetMapping
    Flux<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @PostMapping
    Mono<ResponseEntity<Item>> createItem(@RequestBody Item item) {
        Item result = new Item(UUID.randomUUID().toString(), item.name(), item.price());
        return itemRepository.save(result)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/{itemId}/buy")
    Mono<ResponseEntity<Reward>> buyItem(@PathVariable String itemId) {
        itemRepository.findById(itemId)
                .flatMap(item -> rewardRepository.findAll()
                        .next()
                        .flatMap(reward -> {
                            if (reward.amount() >= item.price()) {
                                return rewardRepository.save(new Reward(reward.id(), reward.amount() - item.price()))
                                        .then(itemRepository.deleteById(itemId));
                            } else {
                                return Mono.empty();
                            }
                        }))
                .subscribe();
        return rewardRepository.findById("1")
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{itemId}")
    Mono<ResponseEntity<Object>> deleteItem(@PathVariable String itemId) {
        return itemRepository.deleteById(itemId)
                .thenReturn(ResponseEntity.noContent().build())
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }

}
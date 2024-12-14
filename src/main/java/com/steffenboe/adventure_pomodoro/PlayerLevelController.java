package com.steffenboe.adventure_pomodoro;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/player")
@CrossOrigin(origins = { "http://localhost:3000" })
public class PlayerLevelController {
    
    private final PlayerLevelRepository playerLevelRepository;

    public PlayerLevelController(PlayerLevelRepository playerLevelRepository) {
        this.playerLevelRepository = playerLevelRepository;
    }

    @PostConstruct
    void setupPlayerLevel() {
        playerLevelRepository.findById("1")
                .switchIfEmpty(playerLevelRepository.save(new PlayerLevel("1", 1, 0)))
                .subscribe();
    }


    @GetMapping("/level")
    Mono<ResponseEntity<PlayerLevel>> getPlayerLevel() {
        return fetchPlayerLevel()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.ok().build());   
    }

    private Mono<PlayerLevel> fetchPlayerLevel() {
        return playerLevelRepository.findById("1")
                .defaultIfEmpty(new PlayerLevel("1", 1, 0));
    }

    @PutMapping("/level")
    Mono<ResponseEntity<PlayerLevel>> updatePlayerLevel(@RequestBody Integer gainedExp) {        
        return fetchPlayerLevel().flatMap(p -> {
            int playerLevel = p.playerLevel();
            int exp = p.exp() + gainedExp;
            if(exp > 100){
                playerLevel++;
                exp = 0;
            }
            return playerLevelRepository.save(new PlayerLevel("1", playerLevel, exp))
                    .map(ResponseEntity::ok)
                    .defaultIfEmpty(ResponseEntity.notFound().build());
        });

    }
        
}

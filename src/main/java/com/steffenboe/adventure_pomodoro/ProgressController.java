package com.steffenboe.adventure_pomodoro;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/progress")
@CrossOrigin(origins = { "https://adventure-pomodoro-j7inpp7h2q-ey.a.run.app/" })
public class ProgressController {

    private final ProgressRepository progressRepository;

    public ProgressController(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @PutMapping
    Mono<ResponseEntity<Progress>> updateProgress(@RequestBody double update) {
        return fetchProgress()
                .flatMap(progress -> {

                    Progress progressEntity;
                    if(update == 1){
                        progressEntity = new Progress("1", 0.0);
                    } else {
                        progressEntity = new Progress("1", update);
                    }
                    return progressRepository.save(progressEntity).map(ResponseEntity::ok)
                            .defaultIfEmpty(ResponseEntity.notFound().build());
                });
    }

    @GetMapping
    Mono<ResponseEntity<Progress>> getProgress() {
        return fetchProgress()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private Mono<Progress> fetchProgress() {
        return progressRepository.findById("1").defaultIfEmpty(new Progress("1", 0.0));
    }
}

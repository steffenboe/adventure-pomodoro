package com.steffenboe.adventure_pomodoro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

@Service
public class DailyTaskGenerator {

    private final TodoRepository todoRepository;
    private final DailyGenerationRepository dailyGenerationRepository;

    private static final Logger logger = LoggerFactory.getLogger(DailyTaskGenerator.class);

    public DailyTaskGenerator(TodoRepository todoRepository, DailyGenerationRepository dailyGenerationRepository) {
        this.todoRepository = todoRepository;
        this.dailyGenerationRepository = dailyGenerationRepository;
    }

    @PostConstruct
    void generateTasks() {
        dailyGenerationRepository.findById("1")
                .defaultIfEmpty(new DailyGeneration("1", LocalDate.now()))
                .flatMap(dailyGeneration -> {
                    if (!dailyGeneration.lastGeneration().equals(LocalDate.now())) {
                        logger.info("Creating daily tasks...");
                        List<Todo> tasks = new ArrayList<>();
                        tasks.add(
                                new Todo(UUID.randomUUID().toString(), "Workout Session", false, "", List.of("New!")));
                        tasks.add(new Todo(UUID.randomUUID().toString(), "Zahnseide benutzen", false, "",
                                List.of("New!")));
                        tasks.add(new Todo(UUID.randomUUID().toString(), "Tour planen", false, "", List.of("New!")));
                        tasks.add(new Todo(UUID.randomUUID().toString(), "Bei einem Freund melden", false, "",
                                List.of("New!")));
                        tasks.add(new Todo(UUID.randomUUID().toString(), "Kühlschrank reinigen", false, "",
                                List.of("New!")));
                        tasks.add(new Todo(UUID.randomUUID().toString(), "Bad putzen", false, "", List.of("New!")));
                        tasks.add(new Todo(UUID.randomUUID().toString(), "Küche putzen", false, "", List.of("New!")));
                        tasks.add(new Todo(UUID.randomUUID().toString(), "Wohnzimmer saugen", false, "",
                                List.of("New!")));
                        tasks.add(new Todo(UUID.randomUUID().toString(), "Flur saugen", false, "", List.of("New!")));
                        tasks.add(new Todo(UUID.randomUUID().toString(), "Sarah küssen", false, "", List.of("New!")));

                        int randomNumberOfTasks = (int) (Math.random() * 5);
                        while (randomNumberOfTasks == 0) {
                            randomNumberOfTasks = (int) (Math.random() * 5);
                        }
                        System.out.println(randomNumberOfTasks);
                        List<Todo> randomTasks = new ArrayList<>();
                        for (int i = 0; i < randomNumberOfTasks; i++) {
                            int randomIndex = (int) (Math.random() * tasks.size());
                            randomTasks.add(tasks.get(randomIndex));
                            tasks.remove(randomIndex);
                        }
                        todoRepository.saveAll(randomTasks).subscribe();
                    }
                    return dailyGenerationRepository.save(new DailyGeneration("1", LocalDate.now()))
                            .map(x -> Mono.just(x));
                }).subscribe();
    }
}

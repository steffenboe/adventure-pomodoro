package com.steffenboe.adventure_pomodoro;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.data.annotation.Id;

public record DailyGeneration(@Id String playerId, LocalDate lastGeneration) {

}

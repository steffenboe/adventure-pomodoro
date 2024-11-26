package com.steffenboe.adventure_pomodoro;

import org.springframework.data.annotation.Id;

public record PlayerLevel(@Id String playerId, int playerLevel, int exp) {
    
}

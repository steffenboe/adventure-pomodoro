package com.steffenboe.adventure_pomodoro;

import org.springframework.data.annotation.Id;

public record Reward(@Id String id, int amount) {
    
}

package com.steffenboe.adventure_pomodoro;

import org.springframework.data.annotation.Id;

public record Progress(@Id String id, double progress) {

}

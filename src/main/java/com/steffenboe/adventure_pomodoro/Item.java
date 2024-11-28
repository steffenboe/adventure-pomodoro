package com.steffenboe.adventure_pomodoro;

import org.springframework.data.annotation.Id;

public record Item(@Id String id, String name, int price) {

}

package com.steffenboe.adventure_pomodoro;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventories")
public class Inventory {

    @Id
    private String playerId;
    private Set<Item> items;

    public Inventory(String playerId, Set<Item> items) {
        this.playerId = playerId;
        this.items = items;
    }

    public Set<Item> getItems() {
        return items;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Inventory addItem(Item item) {
        items.add(item);
        return new Inventory(playerId, new HashSet<Item>(items));
    }

    public Inventory removeItem(String itemId) {
        return new Inventory(playerId,
                new HashSet<Item>(items.stream().filter(item -> !item.id().equals(itemId)).toList()));
    }

}

package com.steffenboe.adventure_pomodoro;

import java.util.ArrayList;
import java.util.List;

public class Todo {

    private String id;
    private String title;
    private boolean completed;
    private List<String> labels;
    private String notes;

    Todo(String id, String title, boolean completed, String notes, List<String> labels) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.labels = new ArrayList<>();
        this.notes = notes;
        this.labels = labels;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getid() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getNotes() {
        return notes;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}

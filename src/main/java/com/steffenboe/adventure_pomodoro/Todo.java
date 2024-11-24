package com.steffenboe.adventure_pomodoro;

public class Todo {

    private String id;
    private String title;
    private boolean completed;
    private String notes;

    Todo(String id, String title, boolean completed, String notes) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.notes = notes;
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
}

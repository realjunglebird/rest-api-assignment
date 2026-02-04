package com.realjunglebird.restapiassignment.model;

public class Task {
    private Long id;
    private String title;
    private Boolean completed;

    public Task(String title) {
        this.title = title;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Boolean isCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }
}

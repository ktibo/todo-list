package com.javarush.todolist.dto;

import com.javarush.todolist.domain.Status;

public class TaskDTO {

    private String description;

    private Status status;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

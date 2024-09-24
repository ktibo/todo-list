package com.javarush.todolist.service;

import com.javarush.todolist.dao.TaskDAO;
import com.javarush.todolist.domain.Status;
import com.javarush.todolist.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.*;

@Service
public class TaskService {

    private final TaskDAO taskDAO;

    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public List<Task> getAll(int page) {
        Pageable pageRequest = PageRequest.of(page-1, 10);
        Page<Task> all = taskDAO.findAll(pageRequest);
        return all.getContent();
    }

    public int getAllCount() {
        return (int) taskDAO.count();
    }

    @Transactional
    public Task edit(int id, String description, Status status) {
        Task task = taskDAO.getById(id);
        task.setDescription(description);
        task.setStatus(status);
        taskDAO.save(task);
        return task;
    }

    @Transactional
    public Task create(String description, Status status) {
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(status);
        taskDAO.save(task);
        return task;
    }

    @Transactional
    public void delete(int id) {
        Task task = taskDAO.getById(id);
        taskDAO.delete(task);
    }

}

package com.javarush.todolist.controllers;


import com.javarush.todolist.dao.TaskDAO;
import com.javarush.todolist.domain.Task;
import com.javarush.todolist.dto.TaskDTO;
import com.javarush.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@Controller
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    String index(Model model,
                 @RequestParam(value = "page", required = false, defaultValue = "1") int page){
        List<Task> tasks = taskService.getAll(page);
        model.addAttribute("tasks", tasks);
        model.addAttribute("current_page", page);
        int totalPages = (int) Math.ceil(1.0 * taskService.getAllCount() / 10);
        if(totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("page_numbers", pageNumbers);
        }
        return "index";
    }

    @PostMapping("/")
    public String add(Model model,
                      @RequestBody TaskDTO taskDTO) {
        Task task = taskService.create(taskDTO.getDescription(), taskDTO.getStatus());
        return index(model, 1);
    }

    @PostMapping("/{id}")
    public String edit(Model model,
                       @PathVariable Integer id,
                       @RequestBody TaskDTO taskDTO) {

        if (isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id");
        }

        Task task = taskService.edit(id, taskDTO.getDescription(), taskDTO.getStatus());
        return index(model, 1);
    }

    @DeleteMapping("/{id}")
    public String delete(Model model,
                         @PathVariable Integer id) {
        if (isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id");
        }
        taskService.delete(id);
        return index(model, 1);
    }

}


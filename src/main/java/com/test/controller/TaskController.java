package com.test.controller;

import com.test.domain.Status;
import com.test.domain.Task;
import com.test.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {
    private final TaskService taskService;
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String getHomePage(Model model){
        return findPaginated(1,"status","asc",model);
    }
    @GetMapping("/showNewTaskForm")
    public String showNewTaskForm(Model model){
        Task task=new Task();
        model.addAttribute("task", task);
        return "new_task";
    }
    @PostMapping("/saveTask")
    public String saveTask(@ModelAttribute("task") Task task){
        taskService.saveTask(task);
        return "redirect:/";
    }
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model){
        Task task=taskService.getTaskById(id);
        model.addAttribute("task", task);
        return "update_task";
    }
    @GetMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable(value = "id")long id ){
        taskService.deleteTaskById(id);
        return "redirect:/";
    }

    @GetMapping("/page/{pageNum}")
    public String findPaginated(@PathVariable(value = "pageNum") int pageNum,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model){
        int pageSize=5;
        Page<Task> page=taskService.findPaginated(pageNum,pageSize,sortField,sortDir);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listTasks", page.getContent());
        return "index";
    }
}

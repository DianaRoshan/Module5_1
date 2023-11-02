package com.test.service;

import com.test.domain.Task;
import com.test.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repository;
    public List<Task> getAllTasks(){
        return repository.findAll();
    }

    public void saveTask(Task task){
        repository.save(task);
    }

    public void deleteTaskById(long id){
        repository.deleteById(id);
    }

    public Task getTaskById(long id){
        Optional<Task> optionalTask = repository.findById(id);
        if(optionalTask.isPresent()){
            return optionalTask.get();
        }
        throw new RuntimeException("Task not found for id : " + id);
    }

    public Page<Task> findPaginated(int pageNum, int pageSize, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        return repository.findAll(pageable);
    }

}

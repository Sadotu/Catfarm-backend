package team.catfarm.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.catfarm.Exceptions.InvalidTaskException;
import team.catfarm.Exceptions.TaskNotFoundException;
import team.catfarm.Models.Task;
import team.catfarm.Repositories.TaskRepository;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task addTask(Task task) throws InvalidTaskException {
        if (task.getNameTask() == null || task.getNameTask().isEmpty()) {
            throw new InvalidTaskException("Task name cannot be empty.");
        }

        return taskRepository.save(task);
    }

    public Optional<Task> getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) {
            throw new TaskNotFoundException("Task not found with ID: " + id);
        }
        return task;
    }
}

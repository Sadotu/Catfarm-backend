package team.catfarm.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.catfarm.DTO.Input.TaskInputDTO;
import team.catfarm.DTO.Output.TaskOutputDTO;
import team.catfarm.Models.Task;
import team.catfarm.Services.TaskService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/add")
    public ResponseEntity<TaskOutputDTO> addTask(@RequestBody TaskInputDTO taskInputDTO) throws URISyntaxException {
        TaskOutputDTO savedTask = taskService.addTask(taskInputDTO);
        return ResponseEntity.created(new URI("/tasks/" + savedTask.getId())).body(savedTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return ResponseEntity.ok(task.get());
    }

    // create get for tasks of individual users
    // create get for tasks of individual colors
    // create get for tasks of individual labels
    // create get for completed tasks
    // the backend only does the first filter, multiple filters are handled by the frontend

    @PutMapping("/{id}")
    public ResponseEntity<TaskOutputDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskInputDTO taskInputDTO) {
        TaskOutputDTO updatedTask = taskService.updateTaskById(id, taskInputDTO);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }
}

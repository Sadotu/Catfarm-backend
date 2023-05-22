package team.catfarm.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.catfarm.DTO.Input.TaskInputDTO;
import team.catfarm.DTO.Output.EventOutputDTO;
import team.catfarm.DTO.Output.TaskOutputDTO;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Models.Event;
import team.catfarm.Models.Task;
import team.catfarm.Services.TaskService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) throws ResourceNotFoundException {
        Task task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/{filter}")
    public ResponseEntity<List<Task>> getTasksByFilter(@PathVariable String filter) {
        return ResponseEntity.ok(taskService.getTasksByFilter(filter));
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

    @PutMapping("/{id}/event/{event_id}")
    public ResponseEntity<TaskOutputDTO> assignEventToTask(@PathVariable Long id, @PathVariable Long event_id) {
        return ResponseEntity.ok(taskService.assignEventToTask(id, event_id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }
}

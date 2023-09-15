package team.catfarm.Controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.catfarm.DTO.Input.TodoInputDTO;
import team.catfarm.DTO.Output.TodoOutputDTO;
import team.catfarm.Services.TodoService;

import java.util.List;

@RestController
@RequestMapping("/task/todos/{task_id}")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/create")
    public ResponseEntity<List<TodoOutputDTO>> createTodosForTask(@Valid @PathVariable Long task_id, @RequestBody List<TodoInputDTO> todoInputDTOList) {
        return ResponseEntity.ok(todoService.createTodosForTask(task_id, todoInputDTOList));
    }

    @GetMapping("/get")
    public ResponseEntity<List<TodoOutputDTO>> getTodosOfTask(@PathVariable Long task_id) {
        return ResponseEntity.ok(todoService.getTodosOfTask(task_id));
    }

    @PutMapping("/update")
    public ResponseEntity<List<TodoOutputDTO>> updateTodosOfTask(@Valid @PathVariable Long task_id, @RequestBody List<TodoInputDTO> todoInputDTOList) {
        return ResponseEntity.ok(todoService.updateTodosOfTask(task_id, todoInputDTOList));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<TodoOutputDTO>> deleteTodosOfTask(@PathVariable Long task_id, @RequestBody List<Long> todo_ids) {
        return ResponseEntity.ok(todoService.deleteTodosOfTask(task_id, todo_ids));
    }
}

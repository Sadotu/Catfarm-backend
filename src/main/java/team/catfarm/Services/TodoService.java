package team.catfarm.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import team.catfarm.DTO.Input.TodoInputDTO;
import team.catfarm.DTO.Output.TodoOutputDTO;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Models.Task;
import team.catfarm.Models.Todo;
import team.catfarm.Repositories.TaskRepository;
import team.catfarm.Repositories.TodoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final TaskRepository taskRepository;

    public TodoService(TodoRepository todoRepository, TaskRepository taskRepository) {
        this.todoRepository = todoRepository;
        this.taskRepository = taskRepository;
    }

    public TodoOutputDTO transferModelToOutputDTO(Todo todo) {
        TodoOutputDTO todoOutputDTO = new TodoOutputDTO();
        BeanUtils.copyProperties(todo, todoOutputDTO);
        return todoOutputDTO;
    }

    public Todo transferInputDTOToModel(TodoInputDTO todoInputDTO) {
        Todo todo = new Todo();
        BeanUtils.copyProperties(todoInputDTO, todo);
        return todo;
    }

    public List<TodoOutputDTO> createTodosForTask(Long task_id, List<TodoInputDTO> todoInputDTOs) {
        Task task = taskRepository.findById(task_id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + task_id));

        List<Todo> todosToSave = new ArrayList<>();

        for(TodoInputDTO inputDTO : todoInputDTOs) {
            Todo todo = new Todo();
            todo.setDescription(inputDTO.getDescription());
            todo.setCompleted(inputDTO.isCompleted());
            todo.setTask(task);
            todosToSave.add(todo);
        }

        // Save the todos
        List<Todo> savedTodos = todoRepository.saveAll(todosToSave);

        // Convert saved todos to output DTOs
        List<TodoOutputDTO> outputDTOs = savedTodos.stream()
                .map(this::transferModelToOutputDTO)
                .collect(Collectors.toList());

        return outputDTOs;
    }

    public List<TodoOutputDTO> getTodosOfTask(Long task_id) {
        Task task = taskRepository.findById(task_id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + task_id));

        // Retrieve the todos related to the task
        List<Todo> taskTodos = task.getToDos();

        // Convert the todos to the desired output DTO format
        List<TodoOutputDTO> outputDTOs = taskTodos.stream()
                .map(this::transferModelToOutputDTO)
                .collect(Collectors.toList());

        return outputDTOs;
    }
}

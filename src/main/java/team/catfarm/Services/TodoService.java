package team.catfarm.Services;

import jakarta.transaction.Transactional;
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
import java.util.Map;
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

        return savedTodos.stream()
                .map(this::transferModelToOutputDTO)
                .collect(Collectors.toList());
    }

    public List<TodoOutputDTO> getTodosOfTask(Long task_id) {
        Task task = taskRepository.findById(task_id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + task_id));

        // Retrieve the todos related to the task
        List<Todo> taskTodos = task.getToDos();

        return taskTodos.stream()
                .map(this::transferModelToOutputDTO)
                .collect(Collectors.toList());
    }

    public List<TodoOutputDTO> updateTodosOfTask(Long task_id, List<TodoInputDTO> todoInputDTOList) {
        Task task = taskRepository.findById(task_id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + task_id));

        List<Todo> existingTodos = task.getToDos();

        // Convert the existing todos to a map for easier lookup by ID
        Map<Long, Todo> existingTodoMap = existingTodos.stream()
                .collect(Collectors.toMap(Todo::getId, todo -> todo));

        List<Todo> updatedTodos = new ArrayList<>();

        for (TodoInputDTO inputDTO : todoInputDTOList) {
            Todo todo;

            if (inputDTO.getId() != null && existingTodoMap.containsKey(inputDTO.getId())) {
                todo = existingTodoMap.get(inputDTO.getId());
                todo.setDescription(inputDTO.getDescription());
                todo.setCompleted(inputDTO.isCompleted());
            } else {
                todo = new Todo();
                todo.setDescription(inputDTO.getDescription());
                todo.setCompleted(inputDTO.isCompleted());
                todo.setTask(task);
            }

            updatedTodos.add(todo);
        }

        // Save the updated todos
        List<Todo> savedTodos = todoRepository.saveAll(updatedTodos);

        // Convert saved todos to output DTOs
        return savedTodos.stream()
                .map(this::transferModelToOutputDTO)
                .collect(Collectors.toList());
    }

    public List<TodoOutputDTO> deleteTodosOfTask(Long task_id, List<Long> todo_ids) {
        Task task = taskRepository.findById(task_id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + task_id));

        // Fetch todos of the task
        List<Todo> todosOfTask = task.getToDos();

        // Filter todos that match the provided IDs to delete
        List<Todo> todosToDelete = todosOfTask.stream()
                .filter(todo -> todo_ids.contains(todo.getId()))
                .collect(Collectors.toList());

        // Remove the filtered todos from the task's collection
        todosOfTask.removeAll(todosToDelete);

        // Update the task
        taskRepository.save(task);

        // Delete the filtered todos
        todoRepository.deleteAll(todosToDelete);

        // After the deleteAll call
        todo_ids.forEach(id -> {
            if (todoRepository.existsById(id)) {
                throw new RuntimeException("Todo with id " + id + " was not deleted.");
            }
        });

        // Collect the todos that were NOT deleted
        List<Todo> remainingTodos = todosOfTask.stream()
                .filter(todo -> !todo_ids.contains(todo.getId()))
                .collect(Collectors.toList());

        // Convert the remaining todos to output DTOs
        return remainingTodos.stream()
                .map(this::transferModelToOutputDTO)
                .collect(Collectors.toList());
    }
}

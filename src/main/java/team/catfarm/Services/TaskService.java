package team.catfarm.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.catfarm.DTO.Input.TaskInputDTO;
import team.catfarm.DTO.Output.TaskOutputDTO;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Models.Task;
import team.catfarm.Repositories.TaskRepository;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public TaskOutputDTO transferModelToOutputDTO(Task task) {
        TaskOutputDTO taskOutputDTO = new TaskOutputDTO();
        BeanUtils.copyProperties(task, taskOutputDTO);
        return taskOutputDTO;
    }

    public Task transferInputDTOToModel(TaskInputDTO taskInputDTO) {
        Task task = new Task();
        BeanUtils.copyProperties(taskInputDTO, task, "id");
        return task;
    }

    public TaskOutputDTO addTask(TaskInputDTO taskInputDTO) {
        return transferModelToOutputDTO(taskRepository.save(transferInputDTOToModel(taskInputDTO)));
    }

    public Optional<Task> getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) {
            throw new ResourceNotFoundException("Task not found with ID: " + id);
        }
        return task;
    }

    public TaskOutputDTO updateTaskById(Long id, TaskInputDTO taskToUpdateInputDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        BeanUtils.copyProperties(taskToUpdateInputDTO, existingTask, "id");

        return transferModelToOutputDTO(taskRepository.save(existingTask));
    }

    public void deleteTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new ResourceNotFoundException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
    }
}

package team.catfarm.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import team.catfarm.DTO.Input.TaskInputDTO;
import team.catfarm.DTO.Output.TaskOutputDTO;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Models.Event;
import team.catfarm.Models.Task;
import team.catfarm.Repositories.EventRepository;
import team.catfarm.Repositories.TaskRepository;

import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EventRepository eventRepository;

    public TaskService(TaskRepository taskRepository, EventRepository eventRepository) {
        this.taskRepository = taskRepository;
        this.eventRepository = eventRepository;
    }

    public TaskOutputDTO transferModelToOutputDTO(Task task) {
        TaskOutputDTO taskOutputDTO = new TaskOutputDTO();
        BeanUtils.copyProperties(task, taskOutputDTO);
        return taskOutputDTO;
    }

    public Task transferInputDTOToModel(TaskInputDTO taskInputDTO) {
        Task task = new Task();
        BeanUtils.copyProperties(taskInputDTO, task);
        return task;
    }

    public TaskOutputDTO addTask(TaskInputDTO taskInputDTO) {
        if (taskInputDTO.getEvent_id() != null) {
            Optional<Event> eventOptional = eventRepository.findById(taskInputDTO.getEvent_id());
            if (eventOptional.isPresent()) {
                Task task = transferInputDTOToModel(taskInputDTO);
                task.setEvent(eventOptional.get());
                taskRepository.save(task);
                return transferModelToOutputDTO(task);
            }
        } else {
            Task task = taskRepository.save(transferInputDTOToModel(taskInputDTO));
            return transferModelToOutputDTO(task);
        }
        return null;
    }


    public Optional<Task> getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
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

    public TaskOutputDTO assignEventToTask(Long id, Long event_id) throws ResourceNotFoundException {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));

        Event event = eventRepository.findById(event_id)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id " + event_id + " not found"));

        task.setEvent(event);
        taskRepository.save(task);
        return transferModelToOutputDTO(task);
    }

    public void deleteTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new ResourceNotFoundException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
    }
}

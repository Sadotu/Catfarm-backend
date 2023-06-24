package team.catfarm.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import team.catfarm.DTO.Input.TaskInputDTO;
import team.catfarm.DTO.Output.TaskOutputDTO;
import team.catfarm.Exceptions.AccessDeniedException;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Models.Event;
import team.catfarm.Models.File;
import team.catfarm.Models.Task;
import team.catfarm.Models.User;
import team.catfarm.Repositories.EventRepository;
import team.catfarm.Repositories.FileRepository;
import team.catfarm.Repositories.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EventRepository eventRepository;
    private final UserService userService;
    private final FileRepository fileRepository;

    public TaskService(TaskRepository taskRepository, EventRepository eventRepository, UserService userService, FileRepository fileRepository) {
        this.taskRepository = taskRepository;
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.fileRepository = fileRepository;
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


    public TaskOutputDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        return transferModelToOutputDTO(task);
    }

    public List<TaskOutputDTO> getTasksByUser(String user_email) {
        System.out.println(user_email);
        User user = userService.getUser(user_email);
        List<Task> tasks = taskRepository.findByAssignedTo(user);
        List<TaskOutputDTO> taskOutputDTOS = new ArrayList<>();

        for (Task t : tasks) {
            taskOutputDTOS.add(transferModelToOutputDTO(t));
        }
        return taskOutputDTOS;
    }

//    public List<TaskOutputDTO> getTasksByFilter(String filter) {
//        return null;
//    }

    public TaskOutputDTO updateTaskById(Long id, TaskInputDTO taskToUpdateInputDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        BeanUtils.copyProperties(taskToUpdateInputDTO, existingTask, "id");

        return transferModelToOutputDTO(taskRepository.save(existingTask));
    }

    public Long assignEventToTask(Long id, Long event_id) throws ResourceNotFoundException {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));

        Event event = eventRepository.findById(event_id)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id " + event_id + " not found"));

        task.setEvent(event);
        taskRepository.save(task);

        return task.getId();
    }

    public TaskOutputDTO assignFilesToTask(Long id, List<Long> file_id_lst) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        List<File> fileList = new ArrayList<>();
        for (Long f_id : file_id_lst) {
            File file = fileRepository.findById(f_id)
                    .orElseThrow(() -> new ResourceNotFoundException("File not found with id: " + f_id));

            file.setTask(task);
            fileRepository.save(file);
            fileList.add(file);
        }

        task.setFiles(fileList);
        taskRepository.save(task);
        return transferModelToOutputDTO(task);
    }

    public void deleteTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new ResourceNotFoundException("Task not found with ID: " + id);
        }

        Task task = optionalTask.get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        boolean isCurrentUserAssigned = task.getAssignedTo().contains(currentUsername);
        boolean isCurrentUserLion = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_lion"));

        if (!isCurrentUserAssigned && !isCurrentUserLion) {
            throw new AccessDeniedException("You are not authorized to delete this task.");
        }

        taskRepository.deleteById(id);
    }
}

package team.catfarm.Services;

import jakarta.transaction.Transactional;
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
import team.catfarm.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final FileRepository fileRepository;

    public TaskService(TaskRepository taskRepository, EventRepository eventRepository, UserRepository userRepository, UserService userService, FileRepository fileRepository) {
        this.taskRepository = taskRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
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

    public TaskOutputDTO updateTaskById(Long id, TaskInputDTO taskToUpdateInputDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        taskToUpdateInputDTO = checkRelations(existingTask, taskToUpdateInputDTO);
        BeanUtils.copyProperties(taskToUpdateInputDTO, existingTask, "id");

        return transferModelToOutputDTO(taskRepository.save(existingTask));
    }

    public TaskInputDTO checkRelations(Task existingTask, TaskInputDTO taskToUpdateInputDTO) {
        if (taskToUpdateInputDTO.getEvent_id() == null) {
            if (existingTask.getEvent() != null) {
                taskToUpdateInputDTO.setEvent_id(existingTask.getEvent().getId());
            }
        }
        if (taskToUpdateInputDTO.getToDos().isEmpty()) {
            taskToUpdateInputDTO.setToDos(existingTask.getToDos());
        }
        if (taskToUpdateInputDTO.getAssignedTo() == null) {
            taskToUpdateInputDTO.setAssignedTo(existingTask.getAssignedTo());
        }
        if (taskToUpdateInputDTO.getFiles() == null) {
            taskToUpdateInputDTO.setFiles(existingTask.getFiles());
        }
        if (taskToUpdateInputDTO.getCreatedBy() == null) {
            taskToUpdateInputDTO.setCreatedBy(existingTask.getCreatedBy());
        }

        return taskToUpdateInputDTO;
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

        boolean isCurrentUserAssigned = task.getAssignedTo().stream()
                .anyMatch(user -> user.getEmail().equals(currentUsername));
        boolean isCurrentUserLion = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_LION"));

        if (!isCurrentUserAssigned && !isCurrentUserLion) {
            throw new AccessDeniedException("You are not authorized to delete this task.");
        }

        unassignUsersFromTask(id);
        taskRepository.deleteById(id);
    }

    @Transactional
    public void unassignUsersFromTask(Long taskId) {
        try {
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new ResourceNotFoundException("Task with id " + taskId + " does not exist"));

            List<User> assignedToUsers = new ArrayList<>(task.getAssignedTo());

            for (User user : assignedToUsers) {
                user.getTasks().remove(task);
                userRepository.save(user);
            }

            task.getAssignedTo().clear();

            User createdBy = task.getCreatedBy();
            if (createdBy != null) {
                createdBy.getTasks().remove(task);
                userRepository.save(createdBy);
                task.setCreatedBy(null);
            }

            taskRepository.save(task);

        } catch (Exception e) {
            System.out.println("Something went horribly wrong");
        }
    }
}

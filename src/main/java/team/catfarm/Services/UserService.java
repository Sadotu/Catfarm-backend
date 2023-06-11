package team.catfarm.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import team.catfarm.DTO.Input.UserInputDTO;
import team.catfarm.DTO.Output.UserOutputDTO;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Exceptions.UserAlreadyExistsException;
import team.catfarm.Models.Event;
import team.catfarm.Models.Task;
import team.catfarm.Models.User;
import team.catfarm.Repositories.EventRepository;
import team.catfarm.Repositories.TaskRepository;
import team.catfarm.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final EventRepository eventRepository;

    public UserService(UserRepository userRepository, TaskRepository taskRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.eventRepository = eventRepository;
    }

    public UserOutputDTO transferModelToOutputDTO(User user) {
        UserOutputDTO userOutputDTO = new UserOutputDTO();
        BeanUtils.copyProperties(user, userOutputDTO);
        return userOutputDTO;
    }

    public User transferInputDTOToModel(UserInputDTO userInputDTO) {
        User user = new User();
        BeanUtils.copyProperties(userInputDTO, user, "id");
        return user;
    }

    public UserOutputDTO createUser(UserInputDTO userInputDTO) throws UserAlreadyExistsException {
        if (userRepository.findByEmail(userInputDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + userInputDTO.getEmail() + " already exists");
        }
        return transferModelToOutputDTO(userRepository.save(transferInputDTOToModel(userInputDTO)));
    }

    public UserOutputDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));;

        return transferModelToOutputDTO(user);
    }

    public List<UserOutputDTO> getActiveUsers() {
        List<User> activeUserList = userRepository.findByActive(true);
        List<UserOutputDTO> activeUserOutputDTOList = new ArrayList<>();

        for (User u : activeUserList)  { activeUserOutputDTOList.add(transferModelToOutputDTO(u)); }

        return activeUserOutputDTOList;
    }

    public UserOutputDTO updateUser(String email, UserInputDTO userToUpdateInputDTO) {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));

        BeanUtils.copyProperties(userToUpdateInputDTO, existingUser);

        return transferModelToOutputDTO(userRepository.save(existingUser));
    }

    @Transactional
    public UserOutputDTO assignEventToUser(String email, Long event_id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " does not exist"));

        Event event = eventRepository.findById(event_id)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id " + event_id + " does not exist"));

        user.getRsvp().add(event);
        userRepository.save(user);

        event.getRsvp().add(user);
        eventRepository.save(event);
        return transferModelToOutputDTO(user);
    }

    @Transactional
    public UserOutputDTO assignTaskToUser(String email, Long taskId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " does not exist"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + taskId + " does not exist"));

        user.getTasks().add(task);
        userRepository.save(user);

        task.getAssignedTo().add(user);
        taskRepository.save(task);
        return transferModelToOutputDTO(user);
    }

    @Transactional
    public UserOutputDTO userCreatesEvent(String email, Long event_id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " does not exist"));

        Event event = eventRepository.findById(event_id)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id " + event_id + " does not exist"));

        user.getCreatedEvents().add(event);
        userRepository.save(user);

        event.setCreatedBy(user);
        eventRepository.save(event);
        return transferModelToOutputDTO(user);
    }

    @Transactional
    public UserOutputDTO userCreatesTask(String email, Long task_id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " does not exist"));

        Task task = taskRepository.findById(task_id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + task_id + " does not exist"));

        user.getCreatedTasks().add(task);
        userRepository.save(user);

        task.setCreatedBy(user);
        taskRepository.save(task);
        return transferModelToOutputDTO(user);
    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));

        userRepository.delete(user);
    }
}

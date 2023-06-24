package team.catfarm.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.catfarm.DTO.Input.UserInputDTO;
import team.catfarm.DTO.Output.UserOutputDTO;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Exceptions.UserAlreadyExistsException;
import team.catfarm.Models.Authority;
import team.catfarm.Models.Event;
import team.catfarm.Models.Task;
import team.catfarm.Models.User;
import team.catfarm.Repositories.EventRepository;
import team.catfarm.Repositories.TaskRepository;
import team.catfarm.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final EventRepository eventRepository;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

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

        // Encode the password before saving
        String encodedPassword = passwordEncoder.encode(userInputDTO.getPassword());
        userInputDTO.setPassword(encodedPassword);

        return transferModelToOutputDTO(userRepository.save(transferInputDTOToModel(userInputDTO)));
    }


    public UserOutputDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));;

        return transferModelToOutputDTO(user);
    }

    public User getUser(String username) {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }else {
            throw new ResourceNotFoundException(username);
        }
    }

    public List<UserOutputDTO> getActiveUsers() {
        List<User> activeUserList = userRepository.findByEnabled(true);
        List<UserOutputDTO> activeUserOutputDTOList = new ArrayList<>();

        for (User u : activeUserList)  { activeUserOutputDTOList.add(transferModelToOutputDTO(u)); }

        return activeUserOutputDTOList;
    }

    public UserOutputDTO updateUser(String email, UserInputDTO userToUpdateInputDTO) {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));

        // Encode the password before saving
        String encodedPassword = passwordEncoder.encode(userToUpdateInputDTO.getPassword());
        existingUser.setPassword(encodedPassword);

        BeanUtils.copyProperties(userToUpdateInputDTO, existingUser, "password");

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

    // Security

    public Set<Authority> getAuthorities(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(email));

        UserOutputDTO userOutputDTO = transferModelToOutputDTO(user);
        return userOutputDTO.getAuthorities();
    }


    public void addAuthority(String email, String authority) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(email));

        user.addAuthority(new Authority(email, authority));
        userRepository.save(user);
    }


    public void removeAuthority(String email, String authority) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(email));
        Authority authorityToRemove = user.getAuthorities().stream()
                .filter(a -> a.getAuthority().equalsIgnoreCase(authority))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Authority not found"));
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }
}

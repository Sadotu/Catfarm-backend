package team.catfarm.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import team.catfarm.DTO.Input.UserInputDTO;
import team.catfarm.DTO.Output.UserOutputDTO;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Exceptions.UserAlreadyExistsException;
import team.catfarm.Models.Task;
import team.catfarm.Models.User;
import team.catfarm.Repositories.TaskRepository;
import team.catfarm.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public UserService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
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

    public Long assignTaskToUser(String email, Long taskId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " does not exist"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + taskId + " does not exist"));

        user.getTasks().add(task);
        userRepository.save(user);
        return taskId;
    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));

        userRepository.delete(user);
    }
}

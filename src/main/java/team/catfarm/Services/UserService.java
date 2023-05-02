package team.catfarm.Services;

import org.springframework.stereotype.Service;
import team.catfarm.Exceptions.UserAlreadyExistsException;
import team.catfarm.Exceptions.UserNotFoundException;
import team.catfarm.Models.User;
import team.catfarm.Repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email " + email));
    }
}

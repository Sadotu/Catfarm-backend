package team.catfarm.Controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.catfarm.Exceptions.UserAlreadyExistsException;
import team.catfarm.Exceptions.UserNotFoundException;
import team.catfarm.Models.User;
import team.catfarm.DTO.UserDTO;
import team.catfarm.Services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) throws UserAlreadyExistsException {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) throws UserNotFoundException {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{email}/public")
    public ResponseEntity<UserDTO> getUserPublicInfoByEmail(@PathVariable String email) throws UserNotFoundException {
        User user = userService.getUserByEmail(email);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO, "password", "newsletter");
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User userToUpdate) throws UserNotFoundException {
        User updatedUser = userService.updateUser(email, userToUpdate);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{email}")
    public void deleteUser(@PathVariable String email) throws UserNotFoundException {
        userService.deleteUser(email);
    }
}

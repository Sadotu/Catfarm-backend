package team.catfarm.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.catfarm.DTO.Input.UserInputDTO;
import team.catfarm.DTO.Output.UserOutputDTO;
import team.catfarm.Exceptions.UserAlreadyExistsException;
import team.catfarm.Models.User;
import team.catfarm.Services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserOutputDTO> createUser(@RequestBody UserInputDTO userInputDTO) throws UserAlreadyExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userInputDTO));
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

//    @GetMapping("/{email}/public")
//    public ResponseEntity<User> getUserPublicInfoByEmail(@PathVariable String email) {
//        User user = userService.getUserByEmail(email);
//        BeanUtils.copyProperties(user, userOutputDTO, "password", "newsletter");
//        return ResponseEntity.ok(user);
//    }

    @GetMapping("/active")
    public List<User> getActiveUsers() {
        return userService.getActiveUsers();
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserOutputDTO> updateUser(@PathVariable String email, @RequestBody UserInputDTO userToUpdateInputDTO) {
        return ResponseEntity.ok(userService.updateUser(email, userToUpdateInputDTO));
    }

    @DeleteMapping("/{email}")
    public void deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
    }
}

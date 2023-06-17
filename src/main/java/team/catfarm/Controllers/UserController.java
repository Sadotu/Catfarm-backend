package team.catfarm.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.catfarm.DTO.Input.UserInputDTO;
import team.catfarm.DTO.Output.UserOutputDTO;
import team.catfarm.Exceptions.BadRequestException;
import team.catfarm.Exceptions.UserAlreadyExistsException;
import team.catfarm.Services.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserOutputDTO> createUser(@RequestBody UserInputDTO userInputDTO) throws UserAlreadyExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userInputDTO));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserOutputDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/active")
    public List<UserOutputDTO> getActiveUsers() {
        return userService.getActiveUsers();
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<UserOutputDTO> updateUser(@PathVariable String email, @RequestBody UserInputDTO userToUpdateInputDTO) {
        return ResponseEntity.ok(userService.updateUser(email, userToUpdateInputDTO));
    }

    @PutMapping("/{email}/rsvp/{event_id}")
    public ResponseEntity<UserOutputDTO> assignEventToUser(@PathVariable String email, @PathVariable Long event_id) {
        return ResponseEntity.ok(userService.assignEventToUser(email, event_id));
    }

    @PutMapping("/{email}/task/{task_id}")
    public ResponseEntity<UserOutputDTO> assignTaskToUser(@PathVariable String email, @PathVariable Long task_id) {
        return ResponseEntity.ok(userService.assignTaskToUser(email, task_id));
    }

    @PutMapping("/{email}/usercreatesevent/{event_id}")
    public ResponseEntity<UserOutputDTO> userCreatesEvent(@PathVariable String email, @PathVariable Long event_id) {
        return ResponseEntity.ok(userService.userCreatesEvent(email, event_id));
    }

    @PutMapping("/{email}/usercreatestask/{task_id}")
    public ResponseEntity<UserOutputDTO> userCreatesTask(@PathVariable String email, @PathVariable Long task_id) {
        return ResponseEntity.ok(userService.userCreatesTask(email, task_id));
    }

    @DeleteMapping("/delete/{email}")
    public void deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
    }

//    @GetMapping(value = "/{username}/authorities")
//    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
//        return ResponseEntity.ok().body(userService.getAuthorities(username));
//    }
//
//    @PostMapping(value = "/{username}/authorities")
//    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
//        try {
//            String authorityName = (String) fields.get("authority");
//            userService.addAuthority(username, authorityName);
//            return ResponseEntity.noContent().build();
//        } catch (Exception ex) {
//            throw new BadRequestException();
//        }
//    }
//
//    @DeleteMapping(value = "/{username}/authorities/{authority}")
//    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
//        userService.removeAuthority(username, authority);
//
//    }
}
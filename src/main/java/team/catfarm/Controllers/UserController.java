package team.catfarm.Controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping("/create")
    public ResponseEntity<UserOutputDTO> createUser(@RequestBody UserInputDTO userInputDTO) throws UserAlreadyExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userInputDTO));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserOutputDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/enabled")
    public List<UserOutputDTO> getActiveUsers() {
        return userService.getActiveUsers();
    }

    @PutMapping("/update/{email}")
    @PreAuthorize("#email == authentication.principal.email")
    public ResponseEntity<UserOutputDTO> updateUser(@PathVariable String email, @RequestBody UserInputDTO userToUpdateInputDTO) {
        return ResponseEntity.ok(userService.updateUser(email, userToUpdateInputDTO));
    }

    @PutMapping("/{email}/rsvp/{event_id}")
    @PreAuthorize("#email == authentication.principal.email")
    public ResponseEntity<UserOutputDTO> assignEventToUser(@PathVariable String email, @PathVariable Long event_id) {
        return ResponseEntity.ok(userService.assignEventToUser(email, event_id));
    }

    @PutMapping("/{email}/task/{task_id}")
    @PreAuthorize("#email == authentication.principal.email or hasAnyRole('CAT', 'LION')")
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
    @PreAuthorize("#email == authentication.principal.email or hasRole('LION')")
    public void deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
    }

    @GetMapping(value = "/authorities/{email}")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("email") String email) {
        return ResponseEntity.ok().body(userService.getAuthorities(email));
    }

    @PostMapping(value = "/add_authorities/{email}")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("email") String email, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(email, authorityName);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new BadRequestException("An unexpected error occurred");
        }
    }

    @DeleteMapping(value = "/remove_authorities/{email}/{authority}")
    public void deleteUserAuthority(@PathVariable("email") String email, @PathVariable("authority") String authority) {
        userService.removeAuthority(email, authority);
    }
}
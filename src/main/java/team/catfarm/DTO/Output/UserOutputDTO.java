package team.catfarm.DTO.Output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.catfarm.Models.Event;
import team.catfarm.Models.File;
import team.catfarm.Models.Task;
import team.catfarm.Models.User;

import java.util.List;

@Getter
@Setter
public class UserOutputDTO {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String fullName;
    @NotBlank
    private String pronouns;
    @NotBlank
    @Max(value = 120)
    private int age;
    @NotBlank
    @Digits(integer = 14, fraction = 0)
    private String phoneNumber;
    @Size(min=0, max=500)
    private String bio;
    private String role;
    private boolean active;
    private List<Event> rsvp;
    private List<Task> tasks;
    private List<Event> createdEvents;
    private List<Task> createdTasks;
    private File profilePicture;
}

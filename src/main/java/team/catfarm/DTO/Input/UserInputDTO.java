package team.catfarm.DTO.Input;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import team.catfarm.Models.Event;
import team.catfarm.Models.File;
import team.catfarm.Models.Task;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserInputDTO {

    public Boolean enabled;
    private Date creationDate;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min=2, max=75)
    private String fullName;
    @NotBlank
    private String pronouns;
    @NotBlank
    @Max(value = 120)
    private int age;
    @NotBlank
    @Digits(integer = 14, fraction = 0)
    private String phoneNumber;
    @Size(min=0, max=5000)
    private String bio;
    private String password;
    private boolean newsletter;

    //relations
    private List<Event> rsvp;
    private List<Task> tasks;
    private List<Event> createdEvents;
    private List<Task> createdTasks;
    private File profilePicture;
}

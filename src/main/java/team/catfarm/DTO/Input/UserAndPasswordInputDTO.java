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
public class UserAndPasswordInputDTO {

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
    @Min(value = 0)
    @Max(value = 120)
    private int age;
    @Pattern(regexp = "\\+?[0-9-]+")
    private String phoneNumber;
    @Size(min=0, max=5000)
    private String bio;
    private boolean newsletter;
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character (such as !@#$%^&*)")
    private String password;

    //relations
    private List<Event> rsvp;
    private List<Task> tasks;
    private List<Event> createdEvents;
    private List<Task> createdTasks;
    private File profilePicture;
}
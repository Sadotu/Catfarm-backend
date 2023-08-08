package team.catfarm.DTO.Output;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import team.catfarm.Models.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserOutputDTO {
    public Boolean enabled;
    private Date creationDate;
    @JsonSerialize
    public Set<Authority> authorities;
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
    @Size(min=0, max=500)
    private String bio;
    private List<Event> rsvp;
    private List<Task> tasks;
    private List<Event> createdEvents;
    private List<Task> createdTasks;
    private File profilePicture;
}

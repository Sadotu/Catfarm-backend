package team.catfarm.DTO.Input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import team.catfarm.Models.Event;
import team.catfarm.Models.Task;

import java.util.List;

@Getter
@Setter
public class UserInputDTO {
    private String email;
    private String fullName;
    private String pronouns;
    private int age;
    private String phoneNumber;
    private String bio;
    private String password;
    private boolean newsletter;
    private String role;
    private boolean active;

    private List<Task> tasks;
    @JsonIgnoreProperties("rsvp")
    private List<Event> rsvp;
    private List<Event> createdEvents;
    private List<Task> createdTasks;
}

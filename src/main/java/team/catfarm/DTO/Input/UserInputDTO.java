package team.catfarm.DTO.Input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import team.catfarm.Models.Event;
import team.catfarm.Models.File;
import team.catfarm.Models.Task;

import java.util.List;

@Getter
@Setter
public class UserInputDTO {

    public Boolean enabled;
    public String apiKey;
    private String email;
    private String fullName;
    private String pronouns;
    private int age;
    private String phoneNumber;
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

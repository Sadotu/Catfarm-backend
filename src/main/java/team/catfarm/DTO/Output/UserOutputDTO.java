package team.catfarm.DTO.Output;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    private String email;
    private String fullName;
    private String pronouns;
    private int age;
    private String phoneNumber;
    private String bio;
    private List<Event> rsvp;
    private List<Task> tasks;
    private List<Event> createdEvents;
    private List<Task> createdTasks;
    private File profilePicture;
}

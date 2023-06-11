package team.catfarm.DTO.Output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import team.catfarm.Models.File;
import team.catfarm.Models.Task;
import team.catfarm.Models.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class EventOutputDTO {
    private Long id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private String color;
    private List<Task> tasks;
    private List<User> rsvp;
    private User createdBy;
}

package team.catfarm.DTO.Output;

import lombok.Getter;
import lombok.Setter;
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

    //relations
    private List<Task> tasks;
    private List<User> rsvp;
    private User createdBy;
}

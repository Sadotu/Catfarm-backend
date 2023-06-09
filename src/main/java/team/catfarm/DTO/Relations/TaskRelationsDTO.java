package team.catfarm.DTO.Relations;

import team.catfarm.Models.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TaskRelationsDTO {
    private Long id;
    private String nameTask;
    private LocalDateTime deadline;
    private String description;
    private boolean accepted;
    private boolean completed;
    private User createdBy;
    private List<User> assignedTo;
    private boolean files;
}

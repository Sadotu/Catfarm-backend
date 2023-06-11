package team.catfarm.DTO.Input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import team.catfarm.Models.File;
import team.catfarm.Models.User;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TaskInputDTO {
    private String nameTask;
    private Date deadline;
    private String description;
    private boolean accepted;
    private boolean completed;
    private Long event_id;
    private List<User> assignedTo;
    private List<File> files;
    private User createdBy;
}

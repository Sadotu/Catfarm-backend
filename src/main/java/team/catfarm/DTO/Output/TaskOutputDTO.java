package team.catfarm.DTO.Output;

import lombok.Getter;
import lombok.Setter;
import team.catfarm.Models.Event;
import team.catfarm.Models.File;
import team.catfarm.Models.User;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TaskOutputDTO {
    private Long id;
    private String nameTask;
    private Date deadline;
    private String description;
    private boolean accepted;
    private boolean completed;
    private Event event;
    private User createdBy;
    private List<File> files;
}

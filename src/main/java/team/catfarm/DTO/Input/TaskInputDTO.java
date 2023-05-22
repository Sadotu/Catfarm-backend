package team.catfarm.DTO.Input;

import lombok.Getter;
import lombok.Setter;
import team.catfarm.Models.Event;

import java.util.Date;

@Getter
@Setter
public class TaskInputDTO {
    private String nameTask;
    private Date deadline;
    private String description;
    private boolean accepted;
    private boolean completed;
    private Long event_id;
}

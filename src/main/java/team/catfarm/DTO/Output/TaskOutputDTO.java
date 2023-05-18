package team.catfarm.DTO.Output;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskOutputDTO {
    private Long id;
    private String nameTask;
    private Date deadline;
    private String description;
    private boolean accepted;
    private boolean completed;
}

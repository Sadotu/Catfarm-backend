package team.catfarm.DTO.Output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import team.catfarm.Models.Event;
import team.catfarm.Models.File;
import team.catfarm.Models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TaskOutputDTO {
    private Long id;
    @NotBlank
    private String nameTask;
    @Future
    private Date deadline;
    @Size(min=0, max=5000)
    private String description;
    private List<String> toDos = new ArrayList<>();
    private boolean completed;
    @JsonIgnore
    private Event event;
    private List<User> assignedTo;
    private List<File> files;
    private User createdBy;
}

package team.catfarm.DTO.Input;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import team.catfarm.Models.File;
import team.catfarm.Models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TaskInputDTO {
    @NotBlank
    private String nameTask;
    @Future
    private Date deadline;
    @Size(min=0, max=5000)
    private String description;
    private List<String> toDos = new ArrayList<>();
    private boolean completed;
    private Long event_id;
    private List<User> assignedTo;
    private List<File> files;
    private User createdBy;
}

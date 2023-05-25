package team.catfarm.DTO.Output;

import lombok.Getter;
import lombok.Setter;
import team.catfarm.Models.File;
import team.catfarm.Models.Task;

import java.time.LocalDateTime;
import java.util.Date;
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
    private List<File> files;
    private List<Task> tasks;
}

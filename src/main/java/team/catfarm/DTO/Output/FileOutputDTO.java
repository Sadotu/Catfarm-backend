package team.catfarm.DTO.Output;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import team.catfarm.Models.Event;
import team.catfarm.Models.Task;
import team.catfarm.Models.User;

import java.util.Date;

@Setter
@Getter
public class FileOutputDTO {
    private Long id;
    private String name;
    private String extension;
    private double size;
    private Date uploadDate;
    private Task task;
    private User user;
}
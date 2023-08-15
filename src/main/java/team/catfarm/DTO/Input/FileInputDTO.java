package team.catfarm.DTO.Input;

import lombok.Getter;
import lombok.Setter;
import team.catfarm.Models.Task;
import team.catfarm.Models.User;

import java.util.Date;

@Setter
@Getter
public class FileInputDTO {
    private Long id;
    private String fileName;
    private String extension;
    private double size;
    private String location;
    private Date uploadDate;
    private Task task;
    private User user;
}

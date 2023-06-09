package team.catfarm.DTO.Output;

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
    private String fileName;
    private String type;
    private String extension;
    private double size;
    private String location;
    private Date uploadDate;
//    private Event event;
    private Task task;
    private User user;
}
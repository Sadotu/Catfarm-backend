package team.catfarm.DTO.Input;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import team.catfarm.Models.Task;
import team.catfarm.Models.User;

import java.util.Date;

@Setter
@Getter
public class FileInputDTO {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String extension;
    @Max(value = 5000)
    private double size;
    private Date uploadDate;
    private Task task;
    private User user;
}

package team.catfarm.DTO.Input;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import team.catfarm.Models.Task;
import team.catfarm.Models.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class EventInputDTO {
    private Long id;
    @NotBlank
    @Size(min=2, max=75)
    private String name;
    @FutureOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;
    @Future
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;
    @Size(min=0, max=500)
    private String description;
    private String color;
    private List<Task> tasks;
    private List<User> rsvp;
    private User createdBy;
}

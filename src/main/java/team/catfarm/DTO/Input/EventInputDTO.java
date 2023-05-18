package team.catfarm.DTO.Input;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class EventInputDTO {
    private Long id;
    private String name;
    private Date date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private String color;
}

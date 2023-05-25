package team.catfarm.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.catfarm.DTO.Input.EventInputDTO;
import team.catfarm.DTO.Output.EventOutputDTO;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Repositories.FileRepository;
import team.catfarm.Repositories.TaskRepository;
import team.catfarm.Repositories.UserRepository;
import team.catfarm.Services.EventService;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final FileRepository fileRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public EventController(EventService eventService, FileRepository fileRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.eventService = eventService;
        this.fileRepository = fileRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<EventOutputDTO> createEvent(@RequestBody EventInputDTO eventInputDTO) throws URISyntaxException {
        EventOutputDTO savedEvent = eventService.createEvent(eventInputDTO);
        return ResponseEntity.created(new URI("/events/" + savedEvent.getId())).body(savedEvent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventOutputDTO> getEventById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @GetMapping("/{start}/{end}")
    public ResponseEntity<List<EventOutputDTO>> getEventsByTimePeriod(@PathVariable LocalDateTime start, @PathVariable LocalDateTime end) {
        return ResponseEntity.ok(eventService.getEventsByTimePeriod(start, end));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventOutputDTO> updateEvent(@PathVariable Long id, @RequestBody EventInputDTO eventToUpdateInputDTO) throws ResourceNotFoundException {
        EventOutputDTO updatedEvent = eventService.updateEvent(id, eventToUpdateInputDTO);
        return ResponseEntity.ok(updatedEvent);
    }

    @PutMapping("/{id}/files")
    public EventOutputDTO assignFilesToEvent(@PathVariable Long id, @RequestBody List<Long> file_id_lst) {
        return eventService.assignFilesToEvent(id, file_id_lst);
    }

    @PutMapping("/{id}/tasks")
    public EventOutputDTO assignTasksToEvent(@PathVariable Long id, @RequestBody List<Long> task_id_lst) {
        return eventService.assignTasksToEvent(id, task_id_lst);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) throws ResourceNotFoundException {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

}

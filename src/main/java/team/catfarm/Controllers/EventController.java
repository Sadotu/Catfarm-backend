package team.catfarm.Controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.catfarm.DTO.Input.EventInputDTO;
import team.catfarm.DTO.Output.EventOutputDTO;
import team.catfarm.Exceptions.InvalidEventException;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Services.EventService;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) { this.eventService = eventService; }

    @PostMapping("/add")
    public ResponseEntity<EventOutputDTO> createEvent(@Valid @RequestBody EventInputDTO eventInputDTO) throws URISyntaxException, InvalidEventException {
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

    @PutMapping("/update/{id}")
    public ResponseEntity<EventOutputDTO> updateEvent(@PathVariable Long id, @RequestBody EventInputDTO eventToUpdateInputDTO) throws ResourceNotFoundException, InvalidEventException {
        EventOutputDTO updatedEvent = eventService.updateEvent(id, eventToUpdateInputDTO);
        return ResponseEntity.ok(updatedEvent);
    }

    @PutMapping("/{id}/tasks/{task_id}")
    public ResponseEntity<EventOutputDTO> assignTaskToEvent(@PathVariable Long id, @PathVariable Long task_id) {
        return ResponseEntity.ok(eventService.assignTaskToEvent(id, task_id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) throws ResourceNotFoundException {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

}

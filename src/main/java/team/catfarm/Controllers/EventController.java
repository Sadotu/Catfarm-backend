package team.catfarm.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.catfarm.DTO.Input.EventInputDTO;
import team.catfarm.DTO.Output.EventOutputDTO;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Models.Event;
import team.catfarm.Services.EventService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/add")
    public ResponseEntity<EventOutputDTO> createEvent(@RequestBody EventInputDTO event) throws URISyntaxException {
        event.setDate(new Date());
        EventOutputDTO savedEvent = eventService.createEvent(event);
        return ResponseEntity.created(new URI("/events/" + savedEvent.getId())).body(savedEvent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) throws ResourceNotFoundException {
        Event event = eventService.getEventById(id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventOutputDTO> updateEvent(@PathVariable Long id, @RequestBody EventInputDTO eventToUpdateInputDTO) throws ResourceNotFoundException {
        EventOutputDTO updatedEvent = eventService.updateEvent(id, eventToUpdateInputDTO);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) throws ResourceNotFoundException {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

}

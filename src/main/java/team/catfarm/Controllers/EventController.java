package team.catfarm.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.catfarm.Exceptions.EventNotFoundException;
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

    @PostMapping
    public ResponseEntity<Event> addEvent(@RequestBody Event event) throws URISyntaxException {
        event.setDate(new Date());
        Event savedEvent = eventService.addEvent(event);
        return ResponseEntity.created(new URI("/events/" + savedEvent.getId())).body(savedEvent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) throws EventNotFoundException {
        Event event = eventService.getEventById(id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event eventToUpdate) throws EventNotFoundException {
        Event updatedEvent = eventService.updateEvent(id, eventToUpdate);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) throws EventNotFoundException {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

}

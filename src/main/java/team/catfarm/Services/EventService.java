package team.catfarm.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.catfarm.Exceptions.EventNotFoundException;
import team.catfarm.Models.Event;
import team.catfarm.Repositories.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event getEventById(Long id) throws EventNotFoundException {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id " + id));
    }

    public Event updateEvent(Long id, Event eventToUpdate) throws EventNotFoundException {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id " + id));

        BeanUtils.copyProperties(eventToUpdate, existingEvent, "id");

        return eventRepository.save(existingEvent);
    }

    public void deleteEvent(Long id) {
        Event eventToDelete = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id " + id));

        eventRepository.delete(eventToDelete);
    }
}

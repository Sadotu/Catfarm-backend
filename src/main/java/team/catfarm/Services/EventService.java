package team.catfarm.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.catfarm.DTO.Input.EventInputDTO;
import team.catfarm.DTO.Output.EventOutputDTO;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Models.Event;
import team.catfarm.Repositories.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public EventOutputDTO transferModelToOutputDTO(Event event) {
        EventOutputDTO eventOutputDTO = new EventOutputDTO();
        BeanUtils.copyProperties(event, eventOutputDTO);
        return eventOutputDTO;
    }

    public Event transferInputDTOToModel(EventInputDTO eventInputDTO) {
        Event event = new Event();
        BeanUtils.copyProperties(eventInputDTO, event, "id");
        return event;
    }

    public EventOutputDTO createEvent(EventInputDTO eventInputDTO) {
        return transferModelToOutputDTO(eventRepository.save(transferInputDTOToModel(eventInputDTO)));
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
    }

    public EventOutputDTO updateEvent(Long id, EventInputDTO eventToUpdate) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));

        BeanUtils.copyProperties(eventToUpdate, existingEvent, "id");

        return transferModelToOutputDTO(eventRepository.save(existingEvent));
    }

    public void deleteEvent(Long id) {
        Event eventToDelete = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));

        eventRepository.delete(eventToDelete);
    }
}

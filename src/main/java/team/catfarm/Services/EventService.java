package team.catfarm.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import team.catfarm.DTO.Input.EventInputDTO;
import team.catfarm.DTO.Output.EventOutputDTO;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Models.Event;
import team.catfarm.Repositories.EventRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

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

    public EventOutputDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));

        return transferModelToOutputDTO(event);
    }

    public List<EventOutputDTO> getEventsByTimePeriod(LocalDateTime start, LocalDateTime end) {
        List<Event> eventList = eventRepository.findByStartTimeBetween(start, end);
        List<EventOutputDTO> eventOutputDTOList = new ArrayList<>();

        for (Event e : eventList) {
            EventOutputDTO eventOutputDTO = transferModelToOutputDTO(e);
            eventOutputDTOList.add(eventOutputDTO);
        }

        return eventOutputDTOList;

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

package team.catfarm.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import team.catfarm.DTO.Input.EventInputDTO;
import team.catfarm.DTO.Output.EventOutputDTO;
import team.catfarm.Exceptions.InvalidEventException;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Models.Event;
import team.catfarm.Models.Task;
import team.catfarm.Repositories.EventRepository;
import team.catfarm.Repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final TaskRepository taskRepository;

    public EventService(EventRepository eventRepository, TaskRepository taskRepository) {
        this.eventRepository = eventRepository;
        this.taskRepository = taskRepository;
    }

    public EventOutputDTO transferModelToOutputDTO(Event event) {
        EventOutputDTO eventOutputDTO = new EventOutputDTO();
        BeanUtils.copyProperties(event, eventOutputDTO);
        return eventOutputDTO;
    }

    public boolean isEndTimeLaterThanStartTime(Event event) {
        return event.getEndTime().isAfter(event.getStartTime());
    }

    public Event transferInputDTOToModel(EventInputDTO eventInputDTO) {
        Event event = new Event();
        BeanUtils.copyProperties(eventInputDTO, event, "id");
        return event;
    }

    public EventOutputDTO createEvent(EventInputDTO eventInputDTO) throws InvalidEventException {
        if (isEndTimeLaterThanStartTime(transferInputDTOToModel(eventInputDTO))) {
            return transferModelToOutputDTO(eventRepository.save(transferInputDTOToModel(eventInputDTO)));
        } else {
            throw new InvalidEventException("End time should be later than start time");
        }
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

    public EventOutputDTO updateEvent(Long id, EventInputDTO eventToUpdate) throws InvalidEventException {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));

        BeanUtils.copyProperties(eventToUpdate, existingEvent, "id");

        if (isEndTimeLaterThanStartTime(existingEvent)) {
            return transferModelToOutputDTO(eventRepository.save(existingEvent));
        } else {
            throw new InvalidEventException("End time should be later than start time");
        }
    }

    @Transactional
    public EventOutputDTO assignTaskToEvent(Long id, Long task_id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));

        Task task = taskRepository.findById(task_id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + task_id));

        task.setEvent(event);
        taskRepository.save(task);

        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        event.setTasks(taskList);
        eventRepository.save(event);
        return transferModelToOutputDTO(event);
    }

    public void deleteEvent(Long id) {
        Event eventToDelete = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));

        eventRepository.delete(eventToDelete);
    }
}

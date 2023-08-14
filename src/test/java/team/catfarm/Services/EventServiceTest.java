package team.catfarm.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import team.catfarm.DTO.Input.EventInputDTO;
import team.catfarm.DTO.Output.EventOutputDTO;
import team.catfarm.Exceptions.InvalidEventException;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Models.Event;
import team.catfarm.Models.Task;
import team.catfarm.Repositories.EventRepository;
import team.catfarm.Repositories.TaskRepository;

import java.beans.PropertyDescriptor;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void transferModelToOutputDTO() {
        Event event = new Event();
        event.setId(1L);
        event.setName("Test Event");
        event.setStartTime(LocalDateTime.now());
        event.setEndTime(LocalDateTime.now().plusHours(2));
        event.setDescription("Test Event Description");
        event.setColor("Red");
        event.setTasks(new ArrayList<>());
        event.setRsvp(new ArrayList<>());

        EventOutputDTO outputDTO = eventService.transferModelToOutputDTO(event);

        BeanWrapper eventWrapper = new BeanWrapperImpl(event);
        BeanWrapper dtoWrapper = new BeanWrapperImpl(outputDTO);

        for (PropertyDescriptor descriptor : eventWrapper.getPropertyDescriptors()) {
            String propertyName = descriptor.getName();

            if ("class".equals(propertyName)) {
                continue; // skip the 'class' property
            }

            if (dtoWrapper.isReadableProperty(propertyName) && eventWrapper.isReadableProperty(propertyName)) {
                Object eventValue = eventWrapper.getPropertyValue(propertyName);
                Object dtoValue = dtoWrapper.getPropertyValue(propertyName);

                if (eventValue instanceof Collection) {
                    assertEquals(((Collection<?>) eventValue).size(), ((Collection<?>) dtoValue).size(), "Mismatch in collection size for property: " + propertyName);
                    assertTrue(((Collection<?>) eventValue).containsAll((Collection<?>) dtoValue), "Mismatch in collection content for property: " + propertyName);
                } else {
                    assertEquals(eventValue, dtoValue, "Mismatch in property: " + propertyName);
                }
            }
        }
    }

    @Test
    void transferInputDTOToModel() {
        EventInputDTO inputDTO = new EventInputDTO();
        inputDTO.setName("Test Event");
        inputDTO.setStartTime(LocalDateTime.now());
        inputDTO.setEndTime(LocalDateTime.now().plusHours(2));
        inputDTO.setDescription("Test Event Description");
        inputDTO.setColor("Red");

        Event event = eventService.transferInputDTOToModel(inputDTO);

        BeanWrapper inputDTOWrapper = new BeanWrapperImpl(inputDTO);
        BeanWrapper eventWrapper = new BeanWrapperImpl(event);

        for (PropertyDescriptor descriptor : inputDTOWrapper.getPropertyDescriptors()) {
            String propertyName = descriptor.getName();

            if ("class".equals(propertyName)) {
                continue; // skip the 'class' property
            }

            if (eventWrapper.isReadableProperty(propertyName) && inputDTOWrapper.isReadableProperty(propertyName)) {
                Object inputDTOValue = inputDTOWrapper.getPropertyValue(propertyName);
                Object eventValue = eventWrapper.getPropertyValue(propertyName);

                if (inputDTOValue instanceof Collection) {
                    assertEquals(((Collection<?>) inputDTOValue).size(), ((Collection<?>) eventValue).size(), "Mismatch in collection size for property: " + propertyName);
                    assertTrue(((Collection<?>) inputDTOValue).containsAll((Collection<?>) eventValue), "Mismatch in collection content for property: " + propertyName);
                } else {
                    assertEquals(inputDTOValue, eventValue, "Mismatch in property: " + propertyName);
                }
            }
        }
    }

    @Test
    void isEndTimeLaterThanStartTime() {
        Event event = new Event();
        event.setStartTime(LocalDateTime.now());
        event.setEndTime(LocalDateTime.now().plusHours(1));

        assertTrue(eventService.isEndTimeLaterThanStartTime(event));
    }

    @Test
    void createEventSuccessfully() {
        EventInputDTO eventInputDTO = new EventInputDTO();
        eventInputDTO.setName("Test Event");
        eventInputDTO.setStartTime(LocalDateTime.now());
        eventInputDTO.setEndTime(LocalDateTime.now().plusHours(1));
        eventInputDTO.setDescription("Test Description");
        eventInputDTO.setColor("Red");

        Event expectedEvent = new Event();
        BeanUtils.copyProperties(eventInputDTO, expectedEvent);
        expectedEvent.setId(1L);

        when(eventRepository.save(any(Event.class))).thenReturn(expectedEvent);

        EventOutputDTO resultDTO = assertDoesNotThrow(() -> eventService.createEvent(eventInputDTO));

        assertEquals(expectedEvent.getId(), resultDTO.getId());
        assertEquals(expectedEvent.getName(), resultDTO.getName());
        assertEquals(expectedEvent.getStartTime(), resultDTO.getStartTime());
        assertEquals(expectedEvent.getEndTime(), resultDTO.getEndTime());
        assertEquals(expectedEvent.getDescription(), resultDTO.getDescription());
        assertEquals(expectedEvent.getColor(), resultDTO.getColor());
    }

    @Test
    void createEventWithInvalidTimesUsingAssertThrows() {
        EventInputDTO eventInputDTO = new EventInputDTO();
        eventInputDTO.setStartTime(LocalDateTime.now());
        eventInputDTO.setEndTime(LocalDateTime.now().minusHours(1));

        InvalidEventException exception = assertThrows(InvalidEventException.class, () -> eventService.createEvent(eventInputDTO));

        assertTrue(exception.getMessage().contains("End time should be later than start time"));
    }

    @Test
    void updateEventSuccessfully() {
        Long id = 1L;

        EventInputDTO updateDTO = new EventInputDTO();
        updateDTO.setName("Updated Event");
        updateDTO.setStartTime(LocalDateTime.now());
        updateDTO.setEndTime(LocalDateTime.now().plusHours(1));
        updateDTO.setDescription("Updated Description");
        updateDTO.setColor("Blue");

        Event existingEvent = new Event();
        existingEvent.setId(id);
        existingEvent.setName("Old Event");
        existingEvent.setStartTime(LocalDateTime.now().minusDays(1));
        existingEvent.setEndTime(LocalDateTime.now().minusHours(23));
        existingEvent.setDescription("Old Description");
        existingEvent.setColor("Red");

        when(eventRepository.findById(id)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> {
            Event savedEvent = invocation.getArgument(0);
            savedEvent.setId(id);
            return savedEvent;
        });

        EventOutputDTO resultDTO = assertDoesNotThrow(() -> eventService.updateEvent(id, updateDTO));

        assertEquals(updateDTO.getName(), resultDTO.getName());
        assertEquals(updateDTO.getStartTime(), resultDTO.getStartTime());
        assertEquals(updateDTO.getEndTime(), resultDTO.getEndTime());
        assertEquals(updateDTO.getDescription(), resultDTO.getDescription());
        assertEquals(updateDTO.getColor(), resultDTO.getColor());
    }

    @Test
    void updateEventWithInvalidTimesUsingAssertThrows() {
        Long id = 1L;
        Event existingEvent = new Event();
        existingEvent.setId(id);

        EventInputDTO inputDTO = new EventInputDTO();
        inputDTO.setStartTime(LocalDateTime.now());
        inputDTO.setEndTime(LocalDateTime.now().minusHours(1));

        when(eventRepository.findById(id)).thenReturn(Optional.of(existingEvent));

        InvalidEventException exception = assertThrows(InvalidEventException.class, () -> eventService.updateEvent(id, inputDTO));

        assertTrue(exception.getMessage().contains("End time should be later than start time"));
    }

    @Test
    void getEventById() {
        Event event = new Event();
        event.setId(1L);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        EventOutputDTO result = eventService.getEventById(1L);

        assertEquals(event.getId(), result.getId());
    }

    @Test
    void getEventByIdNotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> eventService.getEventById(1L));
    }

    @Test
    void getEventsByTimePeriod() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        Event event1 = new Event();
        event1.setId(1L);
        Event event2 = new Event();
        event2.setId(2L);

        when(eventRepository.findByStartTimeBetween(start, end)).thenReturn(Arrays.asList(event1, event2));

        List<EventOutputDTO> results = eventService.getEventsByTimePeriod(start, end);

        assertEquals(2, results.size());
        assertEquals(event1.getId(), results.get(0).getId());
        assertEquals(event2.getId(), results.get(1).getId());
    }

    @Test
    void assignTaskToEvent() {
        Long eventId = 1L;
        Long taskId = 10L;
        Event event = new Event();
        event.setId(eventId);

        Task task = new Task();
        task.setId(taskId);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);
        when(eventRepository.save(event)).thenReturn(event);

        EventOutputDTO result = eventService.assignTaskToEvent(eventId, taskId);

        assertEquals(eventId, result.getId());
        // Assert the task is now associated with the event if that data is in the DTO
    }

    @Test
    void deleteEvent() {
        Long id = 1L;
        Event event = new Event();
        event.setId(id);

        when(eventRepository.findById(id)).thenReturn(Optional.of(event));

        eventService.deleteEvent(id);

        verify(eventRepository).delete(event);
    }

    @Test
    void deleteEventNotFound() {
        Long id = 1L;

        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> eventService.deleteEvent(id));
    }
}

package team.catfarm.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import team.catfarm.DTO.Input.EventInputDTO;
import team.catfarm.DTO.Output.EventOutputDTO;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Models.Event;
import team.catfarm.Models.File;
import team.catfarm.Models.Task;
import team.catfarm.Repositories.EventRepository;
import team.catfarm.Repositories.FileRepository;
import team.catfarm.Repositories.TaskRepository;
import team.catfarm.Repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final FileRepository fileRepository;
    private final TaskRepository taskRepository;

    public EventService(EventRepository eventRepository, FileRepository fileRepository, TaskRepository taskRepository) {
        this.eventRepository = eventRepository;
        this.fileRepository = fileRepository;
        this.taskRepository = taskRepository;
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

//    public EventOutputDTO assignFilesToEvent(Long id, List<Long> file_id_lst) {
//        Event event = eventRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
//
//        List<File> fileList = new ArrayList<>();
//        for (Long f_id : file_id_lst) {
//            File file = fileRepository.findById(f_id)
//                    .orElseThrow(() -> new ResourceNotFoundException("File not found with id: " + f_id));
//
//            file.setEvent(event);
//            fileRepository.save(file);
//            fileList.add(file);
//        }
//
//        event.setFiles(fileList);
//        eventRepository.save(event);
//        return transferModelToOutputDTO(event);
//    }

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

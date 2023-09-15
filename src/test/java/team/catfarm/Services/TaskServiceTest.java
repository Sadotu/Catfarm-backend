package team.catfarm.Services;

// [Import statements...]

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import team.catfarm.DTO.Input.TaskInputDTO;
import team.catfarm.DTO.Output.TaskOutputDTO;
import team.catfarm.Exceptions.AccessDeniedException;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Models.Event;
import team.catfarm.Models.File;
import team.catfarm.Models.Task;
import team.catfarm.Models.User;
import team.catfarm.Repositories.EventRepository;
import team.catfarm.Repositories.FileRepository;
import team.catfarm.Repositories.TaskRepository;
import team.catfarm.Repositories.UserRepository;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Captor
    private ArgumentCaptor<Task> captor;


    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskService taskService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void transferModelToOutputDTO() {
        Task task = new Task();
        task.setNameTask("Sample Task");

        TaskOutputDTO output = taskService.transferModelToOutputDTO(task);
        assertEquals("Sample Task", output.getNameTask());
    }

    @Test
    void transferInputDTOToModel() {
        TaskInputDTO inputDTO = new TaskInputDTO();
        inputDTO.setNameTask("Sample Task");

        Task task = taskService.transferInputDTOToModel(inputDTO);
        assertEquals("Sample Task", task.getNameTask());
    }

    @Test
    void addTaskWithEvent() {
        TaskInputDTO taskInputDTO = new TaskInputDTO();
        taskInputDTO.setEvent_id(1L);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(new Event()));

        TaskOutputDTO outputDTO = taskService.addTask(taskInputDTO);
        assertNotNull(outputDTO);
    }

    @Test
    void addTaskWithoutEvent() {
        TaskInputDTO taskInputDTO = new TaskInputDTO();
        taskInputDTO.setNameTask("Sample Task");
        taskInputDTO.setDescription("Sample Description");

        Task mockTask = new Task();
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        TaskOutputDTO outputDTO = taskService.addTask(taskInputDTO);
        assertNotNull(outputDTO);
    }

    @Test
    void addTaskWithEventNotFound() {
        TaskInputDTO taskInputDTO = new TaskInputDTO();
        taskInputDTO.setEvent_id(1L);

        // Mock eventRepository to return an empty Optional
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method
        TaskOutputDTO outputDTO = taskService.addTask(taskInputDTO);

        // Assert that the return value is null
        assertNull(outputDTO);
    }

    @Test
    void getTaskByIdFound() {
        Long taskId = 1L;
        Task mockTask = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(mockTask));

        TaskOutputDTO outputDTO = taskService.getTaskById(taskId);

        assertNotNull(outputDTO);
    }

    @Test
    void getTaskByIdNotFound() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskById(taskId));
    }

    @Test
    void getTasksByUser() {
        String userEmail = "test@catfarm.com";
        when(userService.getUser(userEmail)).thenReturn(new User());
        when(taskRepository.findByAssignedTo(any(User.class))).thenReturn(List.of(new Task()));

        List<TaskOutputDTO> tasks = taskService.getTasksByUser(userEmail);
        assertFalse(tasks.isEmpty());
    }

    @Test
    void updateTaskById() {
        TaskInputDTO inputDTO = new TaskInputDTO();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(new Task()));

        Task mockUpdatedTask = new Task();
        when(taskRepository.save(any(Task.class))).thenReturn(mockUpdatedTask);

        TaskOutputDTO outputDTO = taskService.updateTaskById(1L, inputDTO);
        assertNotNull(outputDTO);
    }

    @Test
    void testUpdateEventIdFromExistingTask() {
        // Given
        TaskInputDTO taskToUpdateInputDTO = new TaskInputDTO(); // Event_id is null by default

        Task existingTask = new Task();
        Event mockEvent = new Event();
        mockEvent.setId(5L); // Giving an arbitrary ID to the mock event
        existingTask.setEvent(mockEvent);

        // Call the method
        TaskInputDTO updatedInputDTO = taskService.checkRelations(existingTask, taskToUpdateInputDTO);

        // Assert that the event_id of updatedInputDTO is now set to the event's ID from the existingTask
        assertEquals(5L, updatedInputDTO.getEvent_id());
    }

    @Test
    void assignEventToTask() {
        Task mockTask = new Task();
        mockTask.setId(1L);

        Event mockEvent = new Event();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(mockTask));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(mockEvent));
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask); // Ensure saved Task is returned

        Long taskId = taskService.assignEventToTask(1L, 1L);
        assertEquals(1L, taskId);
    }

    @Test
    void assignFilesToTask() {
        List<Long> files = List.of(1L, 2L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(new Task()));
        when(fileRepository.findById(anyLong())).thenReturn(Optional.of(new File()));

        TaskOutputDTO outputDTO = taskService.assignFilesToTask(1L, files);
        assertNotNull(outputDTO);
        assertFalse(outputDTO.getFiles().isEmpty());
    }

    @Test
    void deleteTaskByIdAuthorizedByRole() {
        Task task = new Task();
        User currentUser = new User();
        currentUser.setEmail("currentUser@catfarm.com");
        currentUser.setFullName("currentUser@catfarm.com");  // Assuming the fullName is the email for this test

        task.setAssignedTo(List.of(currentUser));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_LION"));
        Authentication auth = new UsernamePasswordAuthenticationToken("currentUser@catfarm.com", "password", authorities);

        if(securityContext != null) {
            when(securityContext.getAuthentication()).thenReturn(auth);
            SecurityContextHolder.setContext(securityContext);
        } else {
            throw new RuntimeException("SecurityContext is null");
        }

        assertDoesNotThrow(() -> taskService.deleteTaskById(1L));
    }


    @Test
    void deleteTaskByIdAuthorizedByAssignment() {
        Task task = new Task();
        User currentUser = new User();
        currentUser.setEmail("currentUser@catfarm.com");
        currentUser.setFullName("currentUser@catfarm.com");  // Assuming the fullName is the email for this test, adjust if needed

        task.setAssignedTo(List.of(currentUser));

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_CAT"));
        Authentication auth = new UsernamePasswordAuthenticationToken("currentUser@catfarm.com", "password", authorities);

        if(securityContext != null) {
            when(securityContext.getAuthentication()).thenReturn(auth);
            SecurityContextHolder.setContext(securityContext);
        } else {
            // Handle null case, perhaps log or throw a custom exception
            throw new RuntimeException("SecurityContext is null");
        }

        assertDoesNotThrow(() -> taskService.deleteTaskById(1L));
    }

    @Test
    void deleteTaskByIdUnauthorized() {
        Task task = new Task();
        User otherUser = new User();
        otherUser.setEmail("otherUser@catfarm.com");
        otherUser.setFullName("otherUser@catfarm.com");  // Assuming the fullName is the email for this test

        task.setAssignedTo(List.of(otherUser));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_user"));
        Authentication auth = new UsernamePasswordAuthenticationToken("currentUser@catfarm.com", "password", authorities);

        if(securityContext != null) {
            when(securityContext.getAuthentication()).thenReturn(auth);
            SecurityContextHolder.setContext(securityContext);
        } else {
            throw new RuntimeException("SecurityContext is null");
        }

        assertThrows(AccessDeniedException.class, () -> taskService.deleteTaskById(1L));
    }

    @Test
    void deleteTaskByIdNotFound() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.deleteTaskById(taskId));
    }

    @Test
    public void testUnassignUsersFromTask() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        task.setNameTask("task 1");

        User user = new User();
        user.setEmail("otherUser@catfarm.com");
        user.setFullName("otherUser@catfarm.com");
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(task);
        user.setTasks(taskList);

        ArrayList<User> list = new ArrayList<>();
        list.add(user);
        task.setAssignedTo(list);
        task.setCreatedBy(user);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.save(user)).thenReturn(user);

        taskService.unassignUsersFromTask(taskId);
        verify(taskRepository).save(captor.capture());

        assertTrue(captor.getValue().getAssignedTo().isEmpty());
    }

    @Test
    public void testUnassignUsersFromTaskExceptionCaught() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenThrow(new RuntimeException("Simulated exception"));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        taskService.unassignUsersFromTask(taskId);

        assertEquals("Something went horribly wrong\n", outContent.toString());

        System.setOut(System.out);
    }
}
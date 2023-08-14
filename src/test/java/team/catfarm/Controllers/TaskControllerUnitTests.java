package team.catfarm.Controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import team.catfarm.DTO.Input.TaskInputDTO;
import team.catfarm.DTO.Output.TaskOutputDTO;
import team.catfarm.Services.TaskService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//@WebMvcTest(TaskController.class)
//@ActiveProfiles("test")
public class TaskControllerUnitTests {

//    @Autowired
    private MockMvc mockMvc;
//    @MockBean
    private TaskService taskServiceMock;

//    @Test
//    @WithMockUser(username = "testuser", roles = "USER")
//    void shouldReturnTaskId() throws Exception {
//        Long taskId = 1L;
//        TaskOutputDTO taskOutputDTO = new TaskOutputDTO();
//
//        Mockito.when(taskServiceMock.getTaskById(taskId)).thenReturn(taskOutputDTO);
//
//        this.mockMvc
//                .perform(MockMvcRequestBuilders.get("/tasks/{taskId}"))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.taskID").value(1L));
//    }

    @BeforeEach
    public void setUp() {
        taskServiceMock = mock(TaskService.class);
        TaskController taskController = new TaskController(taskServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testGetTaskById() throws Exception {
        Long taskId = 1L;
        TaskOutputDTO taskOutputDTO = new TaskOutputDTO(); // set properties

        when(taskServiceMock.getTaskById(taskId)).thenReturn(taskOutputDTO);

        mockMvc.perform(get("/tasks/" + taskId))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddTask() throws Exception {
        TaskInputDTO taskInputDTO = new TaskInputDTO();
        TaskOutputDTO taskOutputDTO = new TaskOutputDTO();
        taskOutputDTO.setId(1L); // set id to avoid NPE

        // Use the any() matcher to accept any TaskInputDTO
        when(taskServiceMock.addTask(any(TaskInputDTO.class))).thenReturn(taskOutputDTO);

        mockMvc.perform(post("/tasks/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(taskInputDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetTasksByUser() throws Exception {
        String userEmail = "test@example.com";
        List<TaskOutputDTO> tasks = Arrays.asList(new TaskOutputDTO(), new TaskOutputDTO());

        when(taskServiceMock.getTasksByUser(userEmail)).thenReturn(tasks);

        mockMvc.perform(get("/tasks/user_tasks/" + userEmail))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTask() throws Exception {
        Long taskId = 1L;
        TaskInputDTO taskInputDTO = new TaskInputDTO(); // set properties
        TaskOutputDTO taskOutputDTO = new TaskOutputDTO(); // set properties

        when(taskServiceMock.updateTaskById(eq(taskId), eq(taskInputDTO))).thenReturn(taskOutputDTO);

        mockMvc.perform(put("/tasks/update/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(taskInputDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testAssignEventToTask() throws Exception {
        Long taskId = 1L;
        Long eventId = 1L;

        when(taskServiceMock.assignEventToTask(taskId, eventId)).thenReturn(taskId);

        mockMvc.perform(put("/tasks/" + taskId + "/event/" + eventId))
                .andExpect(status().isOk());
    }

    @Test
    public void testAssignFilesToTask() throws Exception {
        Long taskId = 1L;
        List<Long> fileIds = Collections.singletonList(1L);
        TaskOutputDTO taskOutputDTO = new TaskOutputDTO(); // set properties

        when(taskServiceMock.assignFilesToTask(taskId, fileIds)).thenReturn(taskOutputDTO);

        mockMvc.perform(put("/tasks/" + taskId + "/files/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(fileIds)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTaskById() throws Exception {
        Long taskId = 1L;
        doNothing().when(taskServiceMock).deleteTaskById(taskId);

        mockMvc.perform(delete("/tasks/delete/" + taskId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testAuthorizedDeleteTaskById() throws Exception {
        Long taskId = 1L;
        // Mock de beveiligingscontext om een geautoriseerde gebruiker te simuleren
        SecurityContextHolder.getContext().setAuthentication(
                new TestingAuthenticationToken("user", "password", "ROLE_CAT")
        );
        doNothing().when(taskServiceMock).deleteTaskById(taskId);
        mockMvc.perform(delete("/tasks/delete/" + taskId))
                .andExpect(status().isNoContent());
    }

    // Test voor ongeautoriseerde verwijdering
    @Test
    public void testUnauthorizedDeleteTaskById() throws Exception {
        Long taskId = 1L;
        // Mock de beveiligingscontext om een ongeautoriseerde gebruiker te simuleren
        SecurityContextHolder.getContext().setAuthentication(
                new TestingAuthenticationToken("user", "password", "ROLE_DOG")
        );
        mockMvc.perform(delete("/tasks/delete/" + taskId))
                .andExpect(status().isNoContent());
    }
}

package team.catfarm.Controllers;

import org.junit.jupiter.api.AfterEach;
import team.catfarm.Models.Task;
import team.catfarm.Repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaskIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    private Long taskId;

    @BeforeEach
    public void setup() {
        Task testTask = new Task();
        testTask.setNameTask("Test Task");
        testTask.setDeadline(new Date());
        testTask.setDescription("This is a test task.");
        testTask.setCompleted(false);

        taskId = taskRepository.save(testTask).getId();
    }

    @AfterEach
    public void tearDown() {
        if (taskId != null) {
            taskRepository.deleteById(taskId);
        }
    }

    @Test
    public void getTaskByIdTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(response.contains("Test Task"));
    }
}
package team.catfarm.Controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import team.catfarm.Models.Task;
import team.catfarm.Repositories.TaskRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class TaskIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    private Task testTask;

    @BeforeEach
    public void setup() {
        testTask = new Task();
        Date dateIn2028 = Date.from(LocalDate.of(2028, 1, 15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        testTask.setNameTask("Test Task");
        testTask.setDeadline(dateIn2028);
        testTask.setDescription("This is a test task.");
        testTask.setCompleted(false);

        taskRepository.save(testTask);
    }

    @AfterEach
    public void tearDown() {
        if (testTask.getId() != null) {
            taskRepository.deleteById(testTask.getId());
        }
    }

    @Test
    public void testGetTaskById() throws Exception {
        mockMvc.perform(get("/tasks/" + testTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testTask.getId()))
                .andExpect(jsonPath("$.nameTask").value("Test Task"));
    }
}

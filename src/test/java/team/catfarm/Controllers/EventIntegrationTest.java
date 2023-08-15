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
import team.catfarm.Models.Event;
import team.catfarm.Repositories.EventRepository;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class EventIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    private Event testEvent;

    @BeforeEach
    public void setup() {
        testEvent = new Event();
        testEvent.setName("Test Event");
        testEvent.setStartTime(LocalDateTime.now());
        testEvent.setEndTime(LocalDateTime.now().plusHours(2));
        testEvent.setDescription("this is a Test Event");

        eventRepository.save(testEvent);
    }

    @AfterEach
    public void tearDown() {
        if (testEvent.getId() != null) {
            eventRepository.deleteById(testEvent.getId());
        }
    }

    @Test
    public void testGetEventById() throws Exception {
        mockMvc.perform(get("/events/" + testEvent.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testEvent.getId()))
                .andExpect(jsonPath("$.name").value("Test Event"));
    }
}
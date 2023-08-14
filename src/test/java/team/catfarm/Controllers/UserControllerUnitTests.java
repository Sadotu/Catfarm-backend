package team.catfarm.Controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import team.catfarm.DTO.Input.UserInputDTO;
import team.catfarm.DTO.Output.UserOutputDTO;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Models.Authority;
import team.catfarm.Services.UserService;

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerUnitTests {

    private MockMvc mockMvc;

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        UserInputDTO userInputDTO = new UserInputDTO(); // set properties
        UserOutputDTO userOutputDTO = new UserOutputDTO(); // set properties

        when(userServiceMock.createUser(any(UserInputDTO.class))).thenReturn(userOutputDTO);

        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userInputDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        String email = "test@example.com";
        UserOutputDTO userOutputDTO = new UserOutputDTO(); // set properties

        when(userServiceMock.getUserByEmail(email)).thenReturn(userOutputDTO);

        mockMvc.perform(get("/users/" + email))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnExceptionWhenUserNotFound() throws Exception {
        String nonExistingEmail = "non-existing@example.com";

        when(userServiceMock.getUserByEmail(nonExistingEmail))
                .thenThrow(new ResourceNotFoundException("User not found with email " + nonExistingEmail));

        mockMvc.perform(get("/users/" + nonExistingEmail))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetActiveUsers() throws Exception {
        UserOutputDTO userOutputDTO = new UserOutputDTO(); // set properties
        List<UserOutputDTO> users = Collections.singletonList(userOutputDTO);

        when(userServiceMock.getActiveUsers()).thenReturn(users);

        mockMvc.perform(get("/users/enabled"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUser() throws Exception {
        String email = "test@example.com";
        UserInputDTO userInputDTO = new UserInputDTO(); // set properties
        UserOutputDTO userOutputDTO = new UserOutputDTO(); // set properties

        when(userServiceMock.updateUser(email, userInputDTO)).thenReturn(userOutputDTO);

        mockMvc.perform(put("/users/update/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userInputDTO)))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "authorized@example.com", authorities = {"ROLE_CAT"})
    public void shouldReturnExceptionWhenUnauthorizedUserTriesToUpdate() throws Exception {
        String unauthorizedUserEmail = "unauthorized@example.com"; // Match the username in WithMockUser
        UserInputDTO userInputDTO = new UserInputDTO();

        userInputDTO.setEmail("frans@example.com");
        userInputDTO.setAge(30);

        mockMvc.perform(put("/users/update/" + unauthorizedUserEmail)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userInputDTO)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    public void testAssignEventToUser() throws Exception {
        String email = "test@example.com";
        Long eventId = 1L;
        UserOutputDTO userOutputDTO = new UserOutputDTO(); // set properties

        when(userServiceMock.assignEventToUser(email, eventId)).thenReturn(userOutputDTO);

        mockMvc.perform(put("/users/" + email + "/rsvp/" + eventId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "differentUser@example.com", authorities = {"SOME_AUTHORITY"}) // Different username than the one in the path variable
    public void testAssignEventToAnotherUser() throws Exception {
        String email = "test@example.com";
        Long eventId = 1L;

        mockMvc.perform(put("/users/" + email + "/rsvp/" + eventId))
                .andExpect(status().isForbidden()); // Expecting a 403 Forbidden response as the user isn't authorized to assign event to another user
    }

    @Test
    public void testAssignTaskToUser() throws Exception {
        String email = "test@example.com";
        Long taskId = 1L;
        UserOutputDTO userOutputDTO = new UserOutputDTO(); // set properties

        when(userServiceMock.assignTaskToUser(email, taskId)).thenReturn(userOutputDTO);

        mockMvc.perform(put("/users/" + email + "/task/" + taskId))
                .andExpect(status().isOk());
    }

    @Test
    public void testUserCreatesEvent() throws Exception {
        String email = "test@example.com";
        Long eventId = 1L;
        UserOutputDTO userOutputDTO = new UserOutputDTO(); // set properties

        when(userServiceMock.userCreatesEvent(email, eventId)).thenReturn(userOutputDTO);

        mockMvc.perform(put("/users/" + email + "/usercreatesevent/" + eventId))
                .andExpect(status().isOk());
    }

    @Test
    public void testUserCreatesTask() throws Exception {
        String email = "test@example.com";
        Long taskId = 1L;
        UserOutputDTO userOutputDTO = new UserOutputDTO(); // set properties

        when(userServiceMock.userCreatesTask(email, taskId)).thenReturn(userOutputDTO);

        mockMvc.perform(put("/users/" + email + "/usercreatestask/" + taskId))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        String email = "test@example.com";
        doNothing().when(userServiceMock).deleteUser(email);

        mockMvc.perform(delete("/users/delete/" + email))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddUserAuthority() throws Exception {
        String email = "test@example.com";
        Map<String, Object> fields = new HashMap<>();
        fields.put("authority", "ROLE_USER");

        doNothing().when(userServiceMock).addAuthority(email, "ROLE_USER");

        mockMvc.perform(post("/users/add_authorities/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(fields)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetUserAuthorities() throws Exception {
        String email = "test@example.com";

        // Assuming userService.getAuthorities returns a Set
        Set<Authority> authorities = Collections.emptySet();
        when(userServiceMock.getAuthorities(email)).thenReturn(authorities);

        mockMvc.perform(get("/users/authorities/" + email))
                .andExpect(status().isOk());
    }


    @Test
    public void testDeleteUserAuthority() throws Exception {
        String email = "test@example.com";
        String authority = "ROLE_USER";

        doNothing().when(userServiceMock).removeAuthority(email, authority);

        mockMvc.perform(delete("/users/remove_authorities/" + email + "/" + authority))
                .andExpect(status().isOk());
    }
}

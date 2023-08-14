package team.catfarm.Models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testEmail() {
        // Arrange
        String email = "test@example.com";

        // Act
        user.setEmail(email);

        // Assert
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testFullName() {
        // Arrange
        String fullName = "John Doe";

        // Act
        user.setFullName(fullName);

        // Assert
        assertEquals(fullName, user.getFullName());
    }

    @Test
    public void testPronouns() {
        // Arrange
        String pronouns = "he/him";

        // Act
        user.setPronouns(pronouns);

        // Assert
        assertEquals(pronouns, user.getPronouns());
    }

    @Test
    public void testAge() {
        // Arrange
        int age = 25;

        // Act
        user.setAge(age);

        // Assert
        assertEquals(age, user.getAge());
    }

    @Test
    public void testPhoneNumber() {
        // Arrange
        String phoneNumber = "123-456-7890";

        // Act
        user.setPhoneNumber(phoneNumber);

        // Assert
        assertEquals(phoneNumber, user.getPhoneNumber());
    }

    @Test
    public void testBio() {
        // Arrange
        String bio = "Software Engineer";

        // Act
        user.setBio(bio);

        // Assert
        assertEquals(bio, user.getBio());
    }

    @Test
    public void testPassword() {
        // Arrange
        String password = "securepassword";

        // Act
        user.setPassword(password);

        // Assert
        assertEquals(password, user.getPassword());
    }

    @Test
    public void testNewsletter() {
        // Arrange
        boolean newsletter = true;

        // Act
        user.setNewsletter(newsletter);

        // Assert
        assertEquals(newsletter, user.isNewsletter());
    }

    @Test
    public void testEnabled() {
        // Arrange
        boolean enabled = true;

        // Act
        user.setEnabled(enabled);

        // Assert
        assertEquals(enabled, user.isEnabled());
    }

    @Test
    public void testRsvp() {
        // Arrange
        List<Event> rsvp = Arrays.asList(new Event(), new Event());

        // Act
        user.setRsvp(rsvp);

        // Assert
        assertEquals(rsvp, user.getRsvp());
    }

    @Test
    public void testTasks() {
        // Arrange
        List<Task> tasks = Arrays.asList(new Task(), new Task());

        // Act
        user.setTasks(tasks);

        // Assert
        assertEquals(tasks, user.getTasks());
    }

    @Test
    public void testCreatedEvents() {
        // Arrange
        List<Event> createdEvents = Arrays.asList(new Event(), new Event());

        // Act
        user.setCreatedEvents(createdEvents);

        // Assert
        assertEquals(createdEvents, user.getCreatedEvents());
    }

    @Test
    public void testCreatedTasks() {
        // Arrange
        List<Task> createdTasks = Arrays.asList(new Task(), new Task());

        // Act
        user.setCreatedTasks(createdTasks);

        // Assert
        assertEquals(createdTasks, user.getCreatedTasks());
    }

    @Test
    public void testUploadedFiles() {
        // Arrange
        List<File> uploadedFiles = Arrays.asList(new File(), new File());

        // Act
        user.setUploadedFiles(uploadedFiles);

        // Assert
        assertEquals(uploadedFiles, user.getUploadedFiles());
    }

    @Test
    public void testProfilePicture() {
        // Arrange
        File profilePicture = new File();

        // Act
        user.setProfilePicture(profilePicture);

        // Assert
        assertEquals(profilePicture, user.getProfilePicture());
    }

    @Test
    public void testAuthorities() {
        // Arrange
        Authority authority = new Authority();
        Set<Authority> authorities = Set.of(authority);

        // Act
        user.setAuthorities(authorities);

        // Assert
        assertEquals(authorities, user.getAuthorities());
    }
}
package team.catfarm.Models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TaskTest {

    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task();
    }

    @Test
    public void testId() {
        // Arrange
        Long id = 1L;

        // Act
        task.setId(id);

        // Assert
        assertEquals(id, task.getId());
    }

    @Test
    public void testNameTask() {
        // Arrange
        String nameTask = "Test Task";

        // Act
        task.setNameTask(nameTask);

        // Assert
        assertEquals(nameTask, task.getNameTask());
    }

    @Test
    public void testDeadline() {
        // Arrange
        Date deadline = new Date();

        // Act
        task.setDeadline(deadline);

        // Assert
        assertEquals(deadline, task.getDeadline());
    }

    @Test
    public void testDescription() {
        // Arrange
        String description = "This is a test task";

        // Act
        task.setDescription(description);

        // Assert
        assertEquals(description, task.getDescription());
    }

    @Test
    public void testCompleted() {
        // Arrange
        boolean completed = true;

        // Act
        task.setCompleted(completed);

        // Assert
        assertEquals(completed, task.isCompleted());
    }

    @Test
    public void testEvent() {
        // Arrange
        Event event = new Event();

        // Act
        task.setEvent(event);

        // Assert
        assertEquals(event, task.getEvent());
    }

    @Test
    public void testAssignedTo() {
        // Arrange
        List<User> assignedTo = Arrays.asList(new User(), new User());

        // Act
        task.setAssignedTo(assignedTo);

        // Assert
        assertEquals(assignedTo, task.getAssignedTo());
    }

    @Test
    public void testFiles() {
        // Arrange
        List<File> files = Arrays.asList(new File(), new File());

        // Act
        task.setFiles(files);

        // Assert
        assertEquals(files, task.getFiles());
    }

    @Test
    public void testCreatedBy() {
        // Arrange
        User createdBy = new User();

        // Act
        task.setCreatedBy(createdBy);

        // Assert
        assertEquals(createdBy, task.getCreatedBy());
    }
}
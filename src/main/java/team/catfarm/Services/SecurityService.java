package team.catfarm.Services;

import org.springframework.stereotype.Service;
import team.catfarm.Models.Task;
import team.catfarm.Models.User;

import java.util.List;

@Service
public class SecurityService {

    public boolean isOwner(Task task, String currentUsername) {
        // Check if the current user is the owner of the task
        String ownerUsername = task.getCreatedBy().getEmail();
        return currentUsername.equals(ownerUsername);
    }

    public boolean isUserInAssignedTo(Task task, String currentUsername) {
        // Check if the current user is in the assignedTo list of the task
        List<User> assignedTo = task.getAssignedTo();
        return assignedTo.contains(currentUsername);
    }
}

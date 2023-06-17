package team.catfarm.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.catfarm.Models.Task;
import team.catfarm.Models.User;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Task save(Task task);
    List<Task> findByAssignedTo(User user);

}
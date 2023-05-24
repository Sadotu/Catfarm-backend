package team.catfarm.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import team.catfarm.Models.File;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByEventId(Long eventId);
    List<File> findByUserEmail(String userEmail);
    List<File> findByTaskId(Long taskId);
}

package team.catfarm.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import team.catfarm.Models.File;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByFileNameAndLocation(String fileName, String location);
//    @Query("SELECT f FROM File f WHERE f.location LIKE CONCAT(:currentDirectory, '%') AND f.fileName LIKE CONCAT('%', :term, '%')")
//    List<File> searchFiles(String currentDirectory, String term);
    void deleteById(Long id);
}

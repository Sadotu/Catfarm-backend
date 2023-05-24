package team.catfarm.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.catfarm.DTO.Input.FileInputDTO;
import team.catfarm.DTO.Input.TaskInputDTO;
import team.catfarm.DTO.Output.FileOutputDTO;
import team.catfarm.DTO.Output.TaskOutputDTO;
import team.catfarm.Models.File;
import team.catfarm.Services.FileService;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) { this.fileService = fileService; }

    @PostMapping("/upload")
    public ResponseEntity<List<FileOutputDTO>> uploadFiles(@RequestBody List<FileInputDTO> file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fileService.uploadFiles(file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<File> getFileById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(fileService.getFileById(id));
    }

    @GetMapping("/path/{location}")
    public ResponseEntity<List<File>> getFilesByLocation(@PathVariable String location) {
        return ResponseEntity.ok(fileService.getFilesByLocation(location));
    }

    @GetMapping("/events/{event_id}")
    public ResponseEntity<List<File>> getFilesByEventId(@PathVariable("event_id") Long eventId) {
        return ResponseEntity.ok(fileService.getFilesByEventId(eventId));
    }

    @GetMapping("/users/{user_email}")
    public ResponseEntity<List<File>> getFilesByUserId(@PathVariable("user_email") String userEmail) {
        return ResponseEntity.ok(fileService.getFilesByUserEmail(userEmail));
    }

    @GetMapping("/tasks/{task_id}")
    public ResponseEntity<List<File>> getFilesByTaskId(@PathVariable("task_id") Long taskId) {
        return ResponseEntity.ok(fileService.getFilesByTaskId(taskId));
    }

//    @GetMapping("/{Entity}")
//    public ResponseEntity<List<File>> getFilesByEntity(@PathVariable String entity) {
//        return ResponseEntity.ok(fileService.getFilesByEntity(entity));
//    }

//    @GetMapping("/search")
//    public ResponseEntity<List<File>> searchFiles(@RequestParam String term, @RequestParam String currentDirectory) {
//        List<File> searchResults = fileService.searchFiles(term, currentDirectory);
//        return ResponseEntity.ok(searchResults);
//    }

    //putmapping: need DTO for location change
    //putmapping: need DTO for a name change

    @PutMapping("/updatefiles")
    public ResponseEntity<List<FileOutputDTO>> updateFiles(@RequestBody List<FileInputDTO> fileInputDTOList) {
        return ResponseEntity.ok(fileService.updateFilesById(fileInputDTOList));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFileById(@PathVariable long id) {
        fileService.deleteFileById(id);
    }
}

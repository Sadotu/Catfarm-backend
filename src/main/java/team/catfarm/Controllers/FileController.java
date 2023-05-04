package team.catfarm.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.catfarm.Models.File;
import team.catfarm.Services.FileService;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) { this.fileService = fileService; }

    @PostMapping
    public ResponseEntity<File> createFile(@RequestBody File file) {
        File createdFile = fileService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<File> getFileById(@PathVariable("id") Long id) {
        File file = fileService.getFileById(id);
        return ResponseEntity.ok(file);
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<File>> searchFiles(@RequestParam String term, @RequestParam String currentDirectory) {
//        List<File> searchResults = fileService.searchFiles(term, currentDirectory);
//        return ResponseEntity.ok(searchResults);
//    }

    //putmapping: need DTO for location change
    //putmapping: need DTO for a name change

    @DeleteMapping("/delete/{id}")
    public void deleteFileById(@PathVariable long id) {
        fileService.deleteFileById(id);
    }
}

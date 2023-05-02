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
}

package team.catfarm.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.catfarm.DTO.Input.FileInputDTO;
import team.catfarm.DTO.Output.FileOutputDTO;
import team.catfarm.Models.File;
import team.catfarm.Services.FileService;

import java.util.List;

import static team.catfarm.Utils.FileUtil.getFileExtension;
import static team.catfarm.Utils.FileUtil.getMimeType;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) { this.fileService = fileService; }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFiles(@RequestParam("file") List<MultipartFile> files) {
        List<FileOutputDTO> uploadedFiles = fileService.uploadFilesAndMetadata(files);
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadedFiles);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadSingleFile(@PathVariable Long id) {
        File file = fileService.findFileById(id);
        byte[] docFile = file.getDocFile();

        if (docFile == null) {
            throw new RuntimeException("There is no file yet.");
        }

        String fileExtension = getFileExtension(file.getFileName());
        String mimeType = getMimeType(fileExtension);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(mimeType));
        headers.setContentDispositionFormData("attachment", "file" + file.getFileName() + "." + fileExtension);
        headers.setContentLength(docFile.length);

        return new ResponseEntity<>(docFile, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileOutputDTO> getFileById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(fileService.getFileById(id));
    }

    @GetMapping("/path/{location}")
    public ResponseEntity<List<FileOutputDTO>> getFilesByLocation(@PathVariable String location) {
        return ResponseEntity.ok(fileService.getFilesByLocation(location));
    }

    @PutMapping("/update-files")
    public ResponseEntity<List<FileOutputDTO>> updateFiles(@Valid @RequestBody List<FileInputDTO> fileInputDTOList) {
        return ResponseEntity.ok(fileService.updateFilesById(fileInputDTOList));
    }

    @PutMapping("/{file_id}/profile_picture/{user_id}")
    public ResponseEntity<String> assignUserToProfilePicture(@PathVariable Long file_id, @PathVariable String user_id) {
        return ResponseEntity.ok(fileService.assignUserToProfilePicture(file_id, user_id));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFileById(@PathVariable long id) {
        fileService.deleteFileById(id);
    }
}

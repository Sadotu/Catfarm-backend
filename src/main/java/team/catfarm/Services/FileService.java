package team.catfarm.Services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import team.catfarm.Exceptions.FileStorageException;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Models.File;
import team.catfarm.Repositories.FileRepository;

@Service
public class FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File uploadFile(File file) throws FileStorageException {
        String fileName = StringUtils.cleanPath(file.getFileName());

        if (fileName.contains("..")) {
            throw new FileStorageException("Invalid file path.");
        }

        String originalName = fileName;
        int count = 0;
        while (fileRepository.findByFileName(fileName).isPresent()) {
            count++;
            fileName = StringUtils.cleanPath(FilenameUtils.getBaseName(originalName) + "(" + count + ")" + "." + FilenameUtils.getExtension(originalName));
        }

        return fileRepository.save(file);
    }

    public File getFileById(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id: " + id));
    }

//    public List<File> searchFiles(String term, String currentDirectory) {
//        if (term == null || term.trim().isEmpty()) {
//            throw new IllegalArgumentException("Search term cannot be blank.");
//        }
//        if (currentDirectory == null || currentDirectory.trim().isEmpty()) {
//            throw new IllegalArgumentException("Current directory cannot be blank.");
//        }
//
//        List<File> searchResults = fileRepository.searchFiles(term, currentDirectory);
//
//        return searchResults;
//    }

    public void deleteFileById(Long id) throws ResourceNotFoundException {
        if (fileRepository.existsById(id)) {
            fileRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("File not found with id: " + id);
        }
    }
}

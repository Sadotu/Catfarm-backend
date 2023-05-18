package team.catfarm.Services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import team.catfarm.DTO.Input.EventInputDTO;
import team.catfarm.DTO.Input.FileInputDTO;
import team.catfarm.DTO.Output.EventOutputDTO;
import team.catfarm.DTO.Output.FileOutputDTO;
import team.catfarm.Exceptions.FileStorageException;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Models.Event;
import team.catfarm.Models.File;
import team.catfarm.Repositories.FileRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileOutputDTO transferModelToOutputDTO(File file) {
        FileOutputDTO fileOutputDTO = new FileOutputDTO();
        BeanUtils.copyProperties(file, fileOutputDTO);
        return fileOutputDTO;
    }

    public File transferInputDTOToModel(FileInputDTO fileInputDTO) {
        File file = new File();
        BeanUtils.copyProperties(fileInputDTO, file, "id");
        return file;
    }

    public List<FileOutputDTO> uploadFiles(List<FileInputDTO> fileInputDTOList) throws FileStorageException {
//        String fileName = StringUtils.cleanPath(file.getFileName());
//
//        if (fileName.contains("..")) {
//            throw new FileStorageException("Invalid file path.");
//        }
//
//        String originalName = fileName;
//        String location = file.getLocation();
//        int count = 0;
//        while (fileRepository.findByFileNameAndLocation(fileName, location).isPresent()) {
//            count++;
//            fileName = StringUtils.cleanPath(FilenameUtils.getBaseName(originalName) + "(" + count + ")" + "." + FilenameUtils.getExtension(originalName));
//        }
//
//        file.setFileName(fileName);

            List<File> createdFiles = new ArrayList<>();
            for (FileInputDTO t : fileInputDTOList) {
                File createdFile = fileRepository.save(transferInputDTOToModel(t));
                createdFiles.add(createdFile);
            }
            List<FileOutputDTO> createdFilesOutputDTO = new ArrayList<>();
            for (File t : createdFiles) {
                FileOutputDTO filesOutputDTO = transferModelToOutputDTO(t);
                createdFilesOutputDTO.add(filesOutputDTO);
            }
        return createdFilesOutputDTO;
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

    public void deleteFileById(Long id) {
        if (fileRepository.existsById(id)) {
            fileRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("File not found with id: " + id);
        }
    }
}
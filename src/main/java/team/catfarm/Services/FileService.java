package team.catfarm.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import team.catfarm.DTO.Input.FileInputDTO;
import team.catfarm.DTO.Output.FileOutputDTO;
import team.catfarm.Exceptions.FileStorageException;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Models.Event;
import team.catfarm.Models.File;
import team.catfarm.Models.Task;
import team.catfarm.Models.User;
import team.catfarm.Repositories.EventRepository;
import team.catfarm.Repositories.FileRepository;
import team.catfarm.Repositories.TaskRepository;
import team.catfarm.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final EventRepository eventRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public FileService(FileRepository fileRepository, EventRepository eventRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.eventRepository = eventRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
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

                if (t.getTask().getId() != null) {
                    Optional<Task> taskOptional = taskRepository.findById(t.getTask().getId());
                    if (taskOptional.isPresent()) {
                        File file = transferInputDTOToModel(t);
                        file.setTask(taskOptional.get());
                        fileRepository.save(file);
                        createdFiles.add(file);
                    } // add else ifs for users
                } else { createdFiles.add(fileRepository.save(transferInputDTOToModel(t))); }
            }

            List<FileOutputDTO> createdFilesOutputDTO = new ArrayList<>();
            for (File t : createdFiles) {
                FileOutputDTO filesOutputDTO = transferModelToOutputDTO(t);
                createdFilesOutputDTO.add(filesOutputDTO);
            }
        return createdFilesOutputDTO;
    }

    public FileOutputDTO getFileById(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id: " + id));

        return transferModelToOutputDTO(file);
    }

    public List<FileOutputDTO> getFilesByLocation(String location) {
        List<File> fileList = fileRepository.findAll().stream()
                .filter(file -> file.getLocation().equalsIgnoreCase(location))
                .collect(Collectors.toList());
        List<FileOutputDTO> fileOutputDTOList = new ArrayList<>();

        for (File f : fileList) {
            FileOutputDTO fileOutputDTO = transferModelToOutputDTO(f);
            fileOutputDTOList.add(fileOutputDTO);
        }

        return fileOutputDTOList;
    }

//    public List<FileOutputDTO> getFilesByEventId(Long eventId) {
//        List<File> fileList = fileRepository.findByEventId(eventId);
//        List<FileOutputDTO> fileOutputDTOList = new ArrayList<>();
//
//        for (File f : fileList) {
//            FileOutputDTO fileOutputDTO = transferModelToOutputDTO(f);
//            fileOutputDTOList.add(fileOutputDTO);
//        }
//
//        return fileOutputDTOList;
//    }
//
//    public List<FileOutputDTO> getFilesByUserEmail(String email) {
//        List<File> fileList = fileRepository.findByEmail(email);
//        List<FileOutputDTO> fileOutputDTOList = new ArrayList<>();
//
//        for (File f : fileList) {
//            FileOutputDTO fileOutputDTO = transferModelToOutputDTO(f);
//            fileOutputDTOList.add(fileOutputDTO);
//        }
//
//        return fileOutputDTOList;
//    }
//
//    public List<FileOutputDTO> getFilesByTaskId(Long taskId) {
//        List<File> fileList = fileRepository.findByTaskId(taskId);
//        List<FileOutputDTO> fileOutputDTOList = new ArrayList<>();
//
//        for (File f : fileList) {
//            FileOutputDTO fileOutputDTO = transferModelToOutputDTO(f);
//            fileOutputDTOList.add(fileOutputDTO);
//        }
//
//        return fileOutputDTOList;
//    }

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

    public List<FileOutputDTO> updateFilesById(List<FileInputDTO> fileInputDTOList) {
        List<File> updatedFiles = new ArrayList<>();

        for (FileInputDTO f : fileInputDTOList) {
            Optional<File> optionalFileToUpdate = fileRepository.findById(f.getId());
            if (optionalFileToUpdate.isPresent()) {
                BeanUtils.copyProperties(f, optionalFileToUpdate.get());
                updatedFiles.add(optionalFileToUpdate.get());
            } else {
                throw new ResourceNotFoundException("File not found with id: " + f.getId());
            }
        }

        List<FileOutputDTO> updatedFilesOutputDTO = new ArrayList<>();
        for (File t : updatedFiles) {
            FileOutputDTO filesOutputDTO = transferModelToOutputDTO(t);
            updatedFilesOutputDTO.add(filesOutputDTO);
        }

        return updatedFilesOutputDTO;
    }

    public FileOutputDTO assignUserToProfilePicture(Long file_id, String user_id) {
        File file = fileRepository.findById(file_id)
                .orElseThrow(() -> new ResourceNotFoundException("File with id " + file_id + " not found"));

        User user = userRepository.findByEmail(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + user_id + " not found"));

        file.setUser(user);
        fileRepository.save(file);

        user.setProfilePicture(file);
        userRepository.save(user);
        return transferModelToOutputDTO(file);
    }

    public void deleteFileById(Long id) {
        fileRepository.deleteById(id);
    }
}
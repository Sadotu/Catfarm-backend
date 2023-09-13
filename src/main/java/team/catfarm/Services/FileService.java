package team.catfarm.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.catfarm.DTO.Input.FileInputDTO;
import team.catfarm.DTO.Output.FileOutputDTO;
import team.catfarm.Exceptions.FileStorageException;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Models.File;
import team.catfarm.Models.User;
import team.catfarm.Repositories.FileRepository;
import team.catfarm.Repositories.UserRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    public FileService(FileRepository fileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    public FileOutputDTO transferModelToOutputDTO(File file) {
        FileOutputDTO fileOutputDTO = new FileOutputDTO();
        BeanUtils.copyProperties(file, fileOutputDTO);
        return fileOutputDTO;
    }

    public List<FileOutputDTO> uploadFilesAndMetadata(List<MultipartFile> files) throws FileStorageException {
        List<File> createdFiles = new ArrayList<>();

        for (MultipartFile multipartFile : files) {

            File fileEntity = new File();

            // Set the docFile
            try {
                fileEntity.setDocFile(multipartFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                throw new FileStorageException("Failed to store file " + multipartFile.getOriginalFilename());
            }

            // Set the fileName
            String originalFileName = multipartFile.getOriginalFilename();
            fileEntity.setName(originalFileName);

            // Set the extension
            String extension = originalFileName != null && originalFileName.lastIndexOf(".") > 0
                    ? originalFileName.substring(originalFileName.lastIndexOf(".") + 1)
                    : null;
            fileEntity.setExtension(extension);

            // Set the size
            fileEntity.setSize(multipartFile.getSize());

            // Set the uploadDate
            fileEntity.setUploadDate(new Date());

            fileRepository.save(fileEntity);
            createdFiles.add(fileEntity);
        }

        // Convert the created File entities to FileOutputDTO
        List<FileOutputDTO> createdFilesOutputDTO = new ArrayList<>();
        for (File createdFile : createdFiles) {
            FileOutputDTO fileOutputDTO = transferModelToOutputDTO(createdFile);
            createdFilesOutputDTO.add(fileOutputDTO);
        }

        return createdFilesOutputDTO;
    }

    @Transactional
    public File findFileById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }

    public FileOutputDTO getFileById(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id: " + id));

        return transferModelToOutputDTO(file);
    }

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

    public String assignUserToProfilePicture(Long file_id, String user_id) { // TO  DO: implement logic to make sure user can only do this for own account
        File file = fileRepository.findById(file_id)
                .orElseThrow(() -> new ResourceNotFoundException("File with id " + file_id + " not found"));

        User user = userRepository.findByEmail(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + user_id + " not found"));

        file.setUser(user);
        fileRepository.save(file);

        user.setProfilePicture(file);
        userRepository.save(user);
        return "Profile picture with id: " + file.getId() + " is assigned to user: " + user.getEmail();
    }

    public void deleteFileById(Long id) {
        fileRepository.deleteById(id);
    }
}
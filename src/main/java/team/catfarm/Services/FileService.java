package team.catfarm.Services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import team.catfarm.Exceptions.FileStorageException;
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

}

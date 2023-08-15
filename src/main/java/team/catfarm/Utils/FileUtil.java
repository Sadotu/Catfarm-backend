package team.catfarm.Utils;

import org.springframework.http.MediaType;

public class FileUtil {

    /**
     * Gets the file extension from the file name.
     *
     * @param fileName the name of the file
     * @return the file extension (without dot) or an empty string if the file has no extension
     */
    public static String getFileExtension(String fileName) {
        // Check for null or empty file name
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }

        // Find the position of the last dot character in the file name
        int lastDotPosition = fileName.lastIndexOf('.');

        // Check if the dot character is present and not at the start or end of the file name
        if (lastDotPosition > 0 && lastDotPosition < fileName.length() - 1) {
            // Return the extension (without the dot)
            return fileName.substring(lastDotPosition + 1);
        } else {
            // No valid extension found
            return "";
        }
    }

    public static String getMimeType(String fileExtension) {
        switch (fileExtension.toLowerCase()) {
            // Document formats
            case "pdf" -> {
                return MediaType.APPLICATION_PDF_VALUE;
            }
            // Txt & Code files (e.g., .java, .c, .cpp, .py, etc.)
            case "txt", "java", "c", "cpp", "py", "js", "css", "html" -> {
                return MediaType.TEXT_PLAIN_VALUE;
            }
            case "json" -> {
                return MediaType.APPLICATION_JSON_VALUE;
            }
            // Image formats
            case "png" -> {
                return MediaType.IMAGE_PNG_VALUE;
            }
            case "jpg", "jpeg" -> {
                return MediaType.IMAGE_JPEG_VALUE;
            }
            case "gif" -> {
                return MediaType.IMAGE_GIF_VALUE;
            }
            // Default
            default -> {
                return MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }
        }
    }
}
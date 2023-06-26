package team.catfarm.Utils;

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
        return switch (fileExtension.toLowerCase()) {
            // Document formats
            case "pdf" -> "application/pdf";
            case "doc" -> "application/msword";
            case "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "txt" -> "text/plain";

            // Image formats
            case "png" -> "image/png";
            case "jpg", "jpeg" -> "image/jpeg";

            // Audio formats
            case "mp3" -> "audio/mpeg";
            case "wav" -> "audio/wav";
            case "ogg" -> "audio/ogg";
            case "m4a" -> "audio/mp4";

            // Video formats
            case "mp4" -> "video/mp4";
            case "avi" -> "video/x-msvideo";
            case "mov" -> "video/quicktime";
            case "wmv" -> "video/x-ms-wmv";
            case "flv" -> "video/x-flv";
            case "mkv" -> "video/x-matroska";

            // Add more mappings if needed
            default -> "application/octet-stream";
        };
    }
}
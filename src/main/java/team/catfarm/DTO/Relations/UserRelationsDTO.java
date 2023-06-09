package team.catfarm.DTO.Relations;

import lombok.Getter;
import lombok.Setter;
import team.catfarm.Models.File;

@Getter
@Setter
public class UserRelationsDTO {
    private String email;
    private String fullName;
    private String pronouns;
    private int age;
    private String phoneNumber;
    private String bio;
    private String password;
    private boolean newsletter;
    private String role;
    private boolean active;
    private File profilePicture;
}

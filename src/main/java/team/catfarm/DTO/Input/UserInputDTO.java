package team.catfarm.DTO.Input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInputDTO {
    public String email;
    public String fullName;
    public String pronouns;
    public int age;
    public String phoneNumber;
    public String bio;
    public String password;
    public boolean newsletter;
    public String role;
    public boolean active;
}

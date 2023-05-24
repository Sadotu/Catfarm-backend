package team.catfarm.DTO.Output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.catfarm.Models.Task;

import java.util.List;

@Getter
@Setter
public class UserOutputDTO {
    public String email;
    public String name;
    public String fullName;
    public String pronouns;
    public int age;
    public String phoneNumber;
    public String bio;
    public String role;
    public boolean active;
    public List<Task> tasks;
}

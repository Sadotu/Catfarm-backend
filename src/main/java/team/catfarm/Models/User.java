package team.catfarm.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(nullable = false, length = 60, unique = true)
    @Email
    private String email;

    private String name;
    private String fullName;
    private String pronouns;
    private int age;
    private String phoneNumber;
    private String bio;
    private String password;
    private boolean newsletter;
    private String role;
    private boolean active;

//    @OneToMany
//    private List<Task> userTasks;
//    @Lob
//    private byte[] profilePicture;
}

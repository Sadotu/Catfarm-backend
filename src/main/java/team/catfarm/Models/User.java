package team.catfarm.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String fullName;
    private String pronouns;
    private int age;
    private String phoneNumber;
    private String bio;
    private String password;
    private boolean newsletter;
    private String role;
    private boolean active;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "event_user",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private List<Event> rsvp;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "task_user",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_email")
    )
    @JsonIgnore
    private List<Task> tasks;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Event> createdEvents;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Task> createdTasks;

    @OneToMany(mappedBy = "uploadedBy")
    private List<File> uploadedFiles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_picture")
    @JsonIgnore
    private File profilePicture;
}

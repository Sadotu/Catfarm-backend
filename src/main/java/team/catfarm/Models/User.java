package team.catfarm.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
@JsonIgnoreProperties(value = {
        "rsvp",
        "tasks"
})
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
    @Column(nullable = false, length = 255)
    private String password;
    private boolean newsletter;
    private boolean enabled;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", updatable = false)
    private Date creationDate;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "event_user",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private List<Event> rsvp;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "task_user",
            joinColumns = @JoinColumn(name = "user_email"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    @JsonIgnore
    private List<Task> tasks;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Event> createdEvents;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Task> createdTasks;

    @OneToMany(mappedBy = "uploadedBy")
    @JsonIgnore
    private List<File> uploadedFiles;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "profile_picture")
    @JsonIgnore
    private File profilePicture;

    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    public Set<Authority> getAuthorities() { return authorities; }
    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }
    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }
}

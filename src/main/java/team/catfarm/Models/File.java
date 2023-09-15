package team.catfarm.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue
    private Long id;
    private byte[] docFile;
    private String name;
    private String extension;
    private double size;
    private Date uploadDate;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @JsonIgnore
    private Task task;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User uploadedBy;

    @OneToOne(mappedBy = "profilePicture")
    private User user;
}
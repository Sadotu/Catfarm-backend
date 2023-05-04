package team.catfarm.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String fileName;
    private String type;
    private String extension;
    private double size;
    private String location;
    private Date uploadDate;
}
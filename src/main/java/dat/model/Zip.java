package dat.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Zip
{
    @Id
    @Column(name = "z_id", unique = true, nullable = false)
    private Integer id;

    @Column(length = 45)
    private String city;

    @Column(length = 45)
    private String region;

    @Column(length = 45)
    private String municipality;
}
package dat.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "zipcode")
public class Zip
{
    @Id
    @Column(name = "zip", unique = true, nullable = false)
    private Integer id;

    @Column(name = "city_name", length = 45)
    private String city;

    @Column(name = "region_name", length = 45)
    private String region;

    @Column(name = "municipality_name",length = 45)
    private String municipality;

    public Zip(Integer id)
    {
        this.id = id;
    }
}
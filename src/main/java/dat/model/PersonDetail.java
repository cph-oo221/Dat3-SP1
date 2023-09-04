package dat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
public class PersonDetail
{
    @Id
    @MapsId
    private Integer id;

    @Column(length = 45)
    private String surname;

    @Column(length = 100)
    private String email;

    @Column(length = 25)
    private String password;

//    @OneToOne
//    private Person person;

    @ManyToOne
    private Address address;
}
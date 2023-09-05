package dat.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Person
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer p_id;
    private String name;
    private LocalDate birthdate;
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private Set<Interests> interests = new HashSet<>();

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private PersonDetail personDetail;

    public Person(String name, LocalDate birthdate)
    {
        this.name = name;
        this.birthdate = birthdate;
    }

    public void addPersonDetail(PersonDetail personDetail)
    {
        this.personDetail = personDetail;

        if (personDetail != null)
        {
            personDetail.setPerson(this);
        }
    }
}
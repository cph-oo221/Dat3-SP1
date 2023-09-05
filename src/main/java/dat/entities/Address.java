package dat.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id")
    private Integer id;

    @Column(nullable = false, length = 45)
    private String street;

    @Column(nullable = false, length = 10)
    private String number;

    @ManyToOne()
    @JoinColumn(name = "zip")
    private Zip zip;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Person> persons = new HashSet<>();


    public Address(String street, String number, Integer zipcode)
    {
        this.street = street.toLowerCase();
        this.number = number;
        this.zip = new Zip(zipcode);
    }

    public void addZip(Zip zip)
    {
        this.zip = zip;
    }
}
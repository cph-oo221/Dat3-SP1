package dat.entities;

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
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id")
    private Integer id;

    private String street;

    private String number;

    @ManyToOne
    @JoinColumn(name = "z_id")
    private Zip zip;

    @OneToMany
    private PersonDetail personDetail;


    public Address(String street, String number, Zip zip)
    {
        this.street = street;
        this.number = number;
        this.zip = zip;
    }

    public void addZip(Zip zip)
    {
        this.zip = zip;
    }
}
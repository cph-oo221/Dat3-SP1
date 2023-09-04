package dat.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
public class PersonDetail
{
    @Id
    private Integer id;

    @Column(length = 45)
    private String surname;

    @Column(length = 100)
    private String email;

    @Column(length = 25)
    private String password;

    @OneToOne
    @MapsId
    private Person person;

    @ManyToOne
    private Address address;

    @OneToMany(fetch = FetchType.EAGER)
    Set<Phone> phoneSet = new HashSet<>();

    public void addPhone(Phone phone)
    {
        this.phoneSet.add(phone);

        if (phone != null)
        {
            phone.setPersonDetail(this);
        }
    }

    @PrePersist
    public void verifyEmail()
    {
        if (!validEmail())
        {
            throw new IllegalArgumentException("The Email: " + getEmail() + " is not valid");
        }
    }

    @PreUpdate
    public void verifyUpdatedEmail()
    {
        if (!validEmail())
        {
            throw new IllegalArgumentException("The Email: " + getEmail() + " is not valid");
        }
    }

    private boolean validEmail()
    {
        return Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", getEmail());
    }
}
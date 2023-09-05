package dat.entities;

import dat.config.HibernateConfig;
import dat.dao.AddressDAO;
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

    @Column(length = 45, nullable = false)
    private String surname;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 25, nullable = false)
    private String password;

    @OneToOne
    @MapsId
    private Person person;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "personDetail", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
    public void onCreate()
    {
        if (!validEmail())
        {
            throw new IllegalArgumentException("The Email: " + getEmail() + " is not valid");
        }

        if (getPassword().length() < 8 || getPassword().length() > 25)
        {
            throw new IllegalArgumentException("The password: " + getPassword() + " is not valid");
        }

        if (getSurname().length() < 2 || getSurname().length() > 45)
        {
            throw new IllegalArgumentException("The surname: " + getSurname() + " is not valid");
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
package dat.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Person
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer p_id;
    private String name;
    @Column(length = 45, nullable = false)
    private String surname;
    private LocalDate birthdate;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 25, nullable = false)
    private String password;

    // related entities
    @ManyToOne(fetch = FetchType.EAGER)
    private Address address;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Interest> interests = new HashSet<>();

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Phone> phoneSet = new HashSet<>();

    public Person(String name, String surname, LocalDate birthdate, String email, String password, Address address)
    {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
        this.address = address;
        address.addPerson(this);
    }

    public Interest removeInterestManually(Hobby h)
    {
        for(Interest i : interests)
        {
            if(i.getHobby().getH_id() == h.getH_id())
            {
                interests.remove(h);
                h.getInterests().remove(i);
                interests.remove(i);
                return i;
            }
        }
        return null;
    }

    public void addPhone(Phone phone)
    {
        this.phoneSet.add(phone);

        if (phone != null)
        {
            phone.setPerson(this);
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

    public Interest addInterest(Hobby hobby)
    {
        //Creates interest
        Interest interest = new Interest();
        interest.setPerson(this);
        interest.setHobby(hobby);

        //adds interests to sets in Person and Hobby
        getInterests().add(interest);
        hobby.getInterests().add(interest);

        return interest;
    }

    public void removeInterest(Hobby hobby)
    {
        interests.forEach(interest -> {
            if (interest.getHobby().getH_id().equals(hobby.getH_id()))
            {
                interest.setHobby(null);
                interest.setPerson(null);
                interests.remove(interest);
                hobby.getInterests().remove(interest);
            }
        });
    }


    @PreUpdate
    public void verifyUpdatedEmail()
    {
        if (!validEmail())
        {
            throw new IllegalArgumentException("The Email: " + getEmail() + " is not valid");
        }
    }

    @PreRemove
    public void onRemove()
    {
        // remove person from address
        if (getAddress().getPersons().size() >= 1)
        {
            address.removePerson(this);
        }
    }


    private boolean validEmail()
    {
        return Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", getEmail());
    }
}